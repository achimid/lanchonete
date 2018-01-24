function selecionaCategoria(){
    var idCategoria = parseInt($(this).data('idcategoria'));
    $.get("/venda/passoProduto/" + idCategoria, function( data ) {
       $('#passoProduto').html( $($.parseHTML(data)));
       init_passoProduto();
    });
}

function init_passoProduto(){
    $('.btn-collapse-categoria').click();
}


// DOCUMENT READY SEMPRE NO FINAL
$(document).ready(function(){
    $('.btn-categoria').click(selecionaCategoria);
});