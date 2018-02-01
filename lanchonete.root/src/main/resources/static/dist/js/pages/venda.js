$.venda = {
    adicionarItem: function (idProduto, qtde, observacao) {
      var _venda = $.venda.getVenda();
      var _item = {produto: { idProduto : idProduto}, qtde: qtde, observacao: observacao };

      _venda.listaItens.push(_item)

      $.venda.setVenda(_venda);
      return true;
    },
    getVenda: function () {
        var _venda
        if(sessionStorage.venda !== undefined && sessionStorage.venda !== 'undefined') _venda = JSON.parse(sessionStorage.venda);
        if(_venda == undefined){
            _venda = {listaItens : [], mesa: { idMesa: '', descricao : ''}}
        }

        return _venda;
    },
    setVenda: function (_venda) {
        sessionStorage.venda = JSON.stringify(_venda);
    },
  hasItem: function () {
      var _venda = $.venda.getVenda();
      if(_venda === undefined) return false;
      if(_venda.listaItens === undefined) return false;
      if(_venda.listaItens.length === 0) return false;

      return true;
  },
  selecionaMesa: function (idMesa, descricao) {
        var _venda = $.venda.getVenda();

        _venda.mesa.idMesa = idMesa
        _venda.mesa.descricao = descricao

        $.venda.setVenda(_venda);
  },
  hasMesa: function () {
        var _venda = $.venda.getVenda();
        return _venda.mesa.idMesa !== '';
    },
  getMesa: function () {
      var _venda = $.venda.getVenda();
      return _venda.mesa;
  },
  removeItem: function (index) {
    var _venda = $.venda.getVenda();

    if(_venda.listaItens.length === 0) return false;

    _venda.listaItens.splice(index);

    $.venda.setVenda(_venda);
    return true;
  },
  cleanVenda: function () {
      sessionStorage.venda = undefined;
  }
}

function selecionaCategoria(){
    var idCategoria = parseInt($(this).data('idcategoria'));
    $("#passoProduto").load("/venda/passoProduto/" + idCategoria, init_passoProduto);
}

function selecionaProduto(){
    var idProduto = parseInt($(this).data('idproduto'));
    $("#passoOpcoesProduto").load("/venda/passoOpcoesProduto/" + idProduto, init_passoDetalheProduto);
}

function selecionaMesa(){
    if($(this).hasClass('selected')){
        $(this).removeClass('selected');
    }else{
        $('.small-box').removeClass('selected');
        $(this).addClass('selected');
        $.venda.selecionaMesa($('.small-box.selected > .idMesa').val(), $('.small-box.selected > .idMesa').data('descricao'));
        $('.btn-collapse-mesa').click();
    }
}

function passoConfirmar(){
    $.ajax({
      type: "POST",
      url: "/venda/passoConfirmar",
      contentType: "application/json",
      data: JSON.stringify($.venda.getVenda().listaItens) ,
      success: function(data){
        $('#passoConfirmar').html(data);
        if($.venda.hasMesa()){
            $('.lb-origem').text('Origem: ' + $.venda.getMesa().descricao);
        }else{
            $('.lb-origem').text('Origem: Balcão');
        }
      }
    });
}

function btnConcluir(){
    passoConfirmar();
    $('#passoMesa').addClass('hidden');
    $('#passoCategoria').addClass('hidden');
    $('#passoProduto').html('');
    $('#passoOpcoesProduto').html('');
    $('#passoConfirmar').removeClass('hidden');
    $('button[name="btnConcluir"]').addClass('hidden');
    $('.checkout__close').click();
    consolidaBtnFinalizar();
}

function btnFinalizar(){
    if(!$.venda.hasItem()){
        alertify.error('Nenhum item selecionado!');
        return;
    }

    var _venda = {
        'venda' : { 'listaItens' : $.venda.getVenda().listaItens}
    }

    $.ajax({
      type: "POST",
      url: "/venda",
      contentType: "application/json",
      data: JSON.stringify(_venda) ,
      success: function(data){
            consolidaBtnFinalizar();
            alertify.success('Pedido enviado!!');
            setTimeout(function(){
                // no final da reaload e limpa a venda
                location.reload();
            },700)
      }
    });
}

function btnVoltar(){
    $('#passoMesa').removeClass('hidden');
    $('#passoCategoria').removeClass('hidden');
    $('#passoProduto').removeClass('hidden');
    $('#passoOpcoesProduto').removeClass('hidden');
    $('#passoConfirmar').addClass('hidden');
    consolidaBtnFinalizar();
}

function removerItem(){
    var index = $(this).data('item-index');
    if($.venda.removeItem(index)){
        $(this).parent().parent().remove();
        removeLinhaTableCheckout(index);
        alertify.success('Item removido com sucesso!');
    }else{
        alertify.error('Ocorreu um erro ao remover o item!');
    }

    if(!$.venda.hasItem()){
        btnVoltar(); // removeu todos os items, sai da tela de finalização.
    }
}

function init_passoProduto(){
    $('.btn-collapse-categoria').click();
}

function init_passoDetalheProduto(){
    $('.btn-collapse-produto').click();
}

function add_qtde_icon(){
    var item_qtde = parseInt($('input[name="item.qtde"]').val());
    item_qtde++;
    $('input[name="item.qtde"]').val(item_qtde);
}

function remove_qtde_icon(){
    var item_qtde = parseInt($('input[name="item.qtde"]').val());
    if(item_qtde <=1) return;
    item_qtde--;
    $('input[name="item.qtde"]').val(item_qtde);
}

function adicionarItem(){
    var qtde = parseInt($('input[name="item.qtde"]').val());
    var idProduto = $('input[name="produto.idProduto"]').val();
    var observacao = $('textarea[name="item.observacao"]').val();
    var nome = $('.js-produto-nome').text();

    if($.venda.adicionarItem(idProduto, qtde, observacao)){
        addLinhaTableCheckout(nome, qtde, observacao);
        novoItem();
        alertify.success('Item adicionado!')
    }else{
        alertify.warning('Ocorreu um erro ao adicionar o item!')
    }
}

function addLinhaTableCheckout(nome, qtde, observacao){
    var linha = '<tr>' +
                     '<td>' + nome + '<span> | ' + observacao + '</span></td>' +
                     '<td>' + qtde + '</td>' +
                 '</tr>';
    $('.js-tb-itens tr:last').after(linha);
}

function removeLinhaTableCheckout(index){
     $('.js-tb-itens tr').get(index + 1).remove();
}

function novoItem(){
    $('.btn-collapse-categoria').click();
    $("#passoOpcoesProduto").html('');
    $("#passoProduto").html('');
    consolidaBtnConluir();
}

function consolidaBtnFinalizar(){
    if($.venda.hasItem()){
        if($('#passoConfirmar').is(':visible')){
            $('button[name="btnVoltar"]').removeClass('hidden');
            $('button[name="btnFinalizar"]').removeClass('hidden');
            $('button[name="btnConcluir"]').addClass('hidden');
        }else{
            $('button[name="btnVoltar"]').addClass('hidden');
            $('button[name="btnFinalizar"]').addClass('hidden');
            $('button[name="btnConcluir"]').removeClass('hidden');
        }
    }else{
        $('button[name="btnVoltar"]').addClass('hidden');
        $('button[name="btnFinalizar"]').addClass('hidden');
        $('button[name="btnConcluir"]').addClass('hidden');
    }
}

function consolidaBtnConluir(){
    if($.venda.hasItem()){
        $('button[name="btnConcluir"]').removeClass('hidden');
    }else{
        $('button[name="btnConcluir"]').addClass('hidden');
    }
}

// DOCUMENT READY SEMPRE NO FINAL
$(document).ready(function(){

    $.venda.cleanVenda();

    $(document).on('click', '.btn-categoria', selecionaCategoria);
    $(document).on('click', '.btn-produto', selecionaProduto);
    $(document).on('click', '.btn-add-qtde', add_qtde_icon);
    $(document).on('click', '.btn-remove-qtde', remove_qtde_icon);
    $(document).on('click', 'button[name="btnAdicionarItem"]', adicionarItem);
    $(document).on('click', '.small-box', selecionaMesa);
    $(document).on('click', 'button[name="btnVoltar"]', btnVoltar);
    $(document).on('click', 'button[name="btnVoltar2"]', btnVoltar);
    $(document).on('click', 'button[name="btnFinalizar"]', btnFinalizar);
    $(document).on('click', 'button[name="btnConcluir"]', btnConcluir);
    $(document).on('click', '.btnRemoverItem', removerItem);

    loadCheckout()
});


function loadCheckout(){

    $('.divCheckout').html($('#divCheckout').html());
    $('#divCheckout').remove();
    $('.divCheckout').removeClass('hidden');

    [].slice.call( document.querySelectorAll( '.checkout' ) ).forEach( function( el ) {
        var openCtrl = el.querySelector( '.checkout__button' ),
            closeCtrls = el.querySelectorAll( '.checkout__cancel' );

        openCtrl.addEventListener( 'click', function(ev) {
            ev.preventDefault();
            classie.add( el, 'checkout--active' );
            $('.checkout__order').css('width', '400px')
        } );

        [].slice.call( closeCtrls ).forEach( function( ctrl ) {
            ctrl.addEventListener( 'click', function() {
                classie.remove( el, 'checkout--active' );
                $('.checkout__order').css('width', '0px')
            } );
        } );
    } );
}