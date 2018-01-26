$.venda = {
  adicionarItem: function (idProduto, qtde, observacao) {
    var _venda
    if(sessionStorage.venda !== undefined && sessionStorage.venda !== 'undefined') _venda = JSON.parse(sessionStorage.venda);

    var _item = {produto: { idProduto : idProduto }, qtde: qtde, observacao: observacao};
    if(_venda == undefined){
        _venda = {listaItens : [], idMesa: ''}
    }
    _venda.listaItens.push(_item)
    sessionStorage.venda = JSON.stringify(_venda);
    return true;
  },
  hasItem: function () {
      var _venda
      if(sessionStorage.venda !== undefined && sessionStorage.venda !== 'undefined') _venda = JSON.parse(sessionStorage.venda);

      if(_venda === undefined) return false;
      if(_venda.listaItens === undefined) return false;
      if(_venda.listaItens.length === 0) return false;

      return true;
  },
  selecionaMesa: function (idMesa) {
      var _venda
      if(sessionStorage.venda !== undefined && sessionStorage.venda !== 'undefined') _venda = JSON.parse(sessionStorage.venda);

      if(_venda == undefined){
          _venda = {listaItens : [], idMesa: ''}
      }
      _venda.idMesa = idMesa
      sessionStorage.venda = JSON.stringify(_venda);
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
    $.venda.selecionaMesa($('.small-box.selected > .idMesa').val());
    $('.btn-collapse-mesa').click();
}

function btnConcluir(){
    $('#passoMesa').addClass('hidden');
    $('#passoCategoria').addClass('hidden');
    $('#passoProduto').html('');
    $('#passoOpcoesProduto').html('');
    $('#passoConfirmar').removeClass('hidden');
    $('button[name="btnConcluir"]').addClass('hidden');
}

function renderPageConfirmar(){
    $('#passoConfirmar').removeClass('hidden');

}

function btnFinalizar(){


    // no final da reaload e limpa a venda
    location.reload();
}

function btnVoltar(){
    $('#passoMesa').removeClass('hidden');
    $('#passoCategoria').removeClass('hidden');
    $('#passoProduto').removeClass('hidden');
    $('#passoOpcoesProduto').removeClass('hidden');
    $('#passoConfirmar').addClass('hidden');
    $('button[name="btnConcluir"]').removeClass('hidden');
}

function removerItem(){

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
    var idProduto = $('input[name="produto.idProduto"]');
    var observacao = $('textarea[name="item.observacao"]');

    if($.venda.adicionarItem(idProduto, qtde, observacao)){
        novoItem();
    }else{
        alert('Ocorreu um erro ao adicionar o item!');
    }
}

function novoItem(){
    $('.btn-collapse-categoria').click();
    $("#passoOpcoesProduto").html('');
    $("#passoProduto").html('');
    consolidaBtnConluir();
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
    $(document).on('click', 'button[name="btnFinalizar"]', btnFinalizar);
    $(document).on('click', 'button[name="btnConcluir"]', btnConcluir);
    $(document).on('click', '.btnRemoverItem', removerItem);

});

