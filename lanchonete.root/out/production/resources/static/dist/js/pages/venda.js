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
    $('.small-box').removeClass('selected');
    $(this).addClass('selected');
    $.venda.selecionaMesa($('.small-box.selected > .idMesa').val(), $('.small-box.selected > .idMesa').data('descricao'));
    $('.btn-collapse-mesa').click();
}

function passoConfirmar(){
    $.ajax({
      type: "POST",
      url: "/venda/passoConfirmar",
      contentType: "application/json",
      data: JSON.stringify($.venda.getVenda().listaItens) ,
      success: function(data){
        $('#passoConfirmar').html(data)
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
    consolidaBtnFinalizar();
}

function btnFinalizar(){


    // no final da reaload e limpa a venda
    consolidaBtnFinalizar();

    alertify.success('Pedido enviado!!');
    setTimeout(function(){
        location.reload();
    },1000)

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
        alertify.success('Item removido com sucesso!');
    }else{
        alertify.error('Ocorreu um erro ao remover o item!');
    }
    $(this).parent().parent().remove();
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

    if($.venda.adicionarItem(idProduto, qtde, observacao)){
        novoItem();
    }else{
        alertify.warning('Ocorreu um erro ao adicionar o item!')
    }
}

function novoItem(){
    $('.btn-collapse-categoria').click();
    $("#passoOpcoesProduto").html('');
    $("#passoProduto").html('');
    consolidaBtnConluir();
}

function consolidaBtnFinalizar(){
    if($("#passoConfirmar").is(":visible")){
        $('button[name="btnVoltar"]').removeClass('hidden');
        $('button[name="btnFinalizar"]').removeClass('hidden');
        $('button[name="btnConcluir"]').addClass('hidden');
    }else{
        $('button[name="btnVoltar"]').addClass('hidden');
        $('button[name="btnFinalizar"]').addClass('hidden');
        $('button[name="btnConcluir"]').removeClass('hidden');
    }
}

function consolidaBtnConluir(){

    if($.venda.hasItem()){
        $('button[name="btnConcluir"]').removeClass('hidden');
    }else{
        $('button[name="btnConcluir"]').addClass('hidden');
    }
    if($.venda.hasMesa()){
        $('.lb-origem').text('Origem: ' + $.venda.getMesa().descricao);
    }else{
        $('.lb-origem').text('Origem: Balcão');
    }

    //consolidaBtnFinalizar();
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
    $(document).on('click', 'button[name="btnFinalizar"]', btnFinalizar);
    $(document).on('click', 'button[name="btnConcluir"]', btnConcluir);
    $(document).on('click', '.btnRemoverItem', removerItem);

});

