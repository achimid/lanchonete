$.venda = {
  adicionarItem: function (idProduto, qtde) {
    var _venda
    if(sessionStorage.venda != undefined) _venda = JSON.parse(sessionStorage.venda);

    var _item = {produto: { idProduto : idProduto }, qtde: qtde};
    if(_venda == undefined){
        _venda = {listaItens : []}
    }
    _venda.listaItens.push(_item)
    sessionStorage.venda = JSON.stringify(_venda);
    return true;
  },
  hasItem: function () {
      var _venda
      if(sessionStorage.venda != undefined) _venda = JSON.parse(sessionStorage.venda);

      if(_venda == undefined) return false;
      if(_venda.listaItens == undefined) return false;
      if(_venda.listaItens.length === 0) return false;

      return true;
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

    if($.venda.adicionarItem(idProduto, qtde)){
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
    $(document).on('click', '.btn-categoria', selecionaCategoria);
    $(document).on('click', '.btn-produto', selecionaProduto);
    $(document).on('click', '.btn-add-qtde', add_qtde_icon);
    $(document).on('click', '.btn-remove-qtde', remove_qtde_icon);
    $(document).on('click', 'button[name="btnAdicionarItem"]', adicionarItem);
});

