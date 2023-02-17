package br.com.pedrodavi;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;

import javax.transaction.Transactional;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;
import java.util.Optional;

@Path("produtos")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class ProdutoResource {

    @GET
    public List<Produto> buscarTodosProdutos() {
        return Produto.listAll();
    }

    @POST
    @Transactional
    public void cadastrarProduto(CadastrarProdutoDTO cadastrarProdutoDTO) {
        Produto p = new Produto();
        p.nome = cadastrarProdutoDTO.nome;
        p.valor = cadastrarProdutoDTO.valor;
        p.persist();
    }

    @PUT
    @Path("{id}")
    @Transactional
    public void atualizarProduto(@PathParam("id") Long id, CadastrarProdutoDTO cadastrarProdutoDTO) {

        Optional<Produto> produtoOp = Produto.findByIdOptional(id);

        if (produtoOp.isPresent()) {
            Produto produto = produtoOp.get();
            produto.nome = cadastrarProdutoDTO.nome;
            produto.valor = cadastrarProdutoDTO.valor;
            produto.persist();
        } else {
            throw  new NotFoundException();
        }
    }

    @DELETE
    @Path("{id}")
    @Transactional
    public void deletarProduto(@PathParam("id") Long id) {
        Optional<Produto> produtoOp = Produto.findByIdOptional(id);
        produtoOp.ifPresentOrElse(Produto::delete, () -> {throw  new NotFoundException();});
    }

}
