package armazem;

import ingredientes.Base;
import ingredientes.Ingrediente;
import ingredientes.Topping;

import java.util.Objects;
import java.util.TreeMap;

public class Armazem {

    private TreeMap<Ingrediente, Integer> estoque;

    public TreeMap<Ingrediente, Integer> getEstoque() {
        return estoque;
    }

    public void setEstoque(TreeMap<Ingrediente, Integer> estoque) {
        this.estoque = estoque;
    }

    public Armazem() {
        setEstoque(new TreeMap<>());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Armazem)) return false;
        Armazem armazem = (Armazem) o;
        return Objects.equals(estoque, armazem.estoque);
    }

    @Override
    public int hashCode() {
        return Objects.hash(estoque);
    }

    public void cadastrarIngredienteEmEstoque(Ingrediente ingrediente) {
        if(!this.estoque.containsKey(ingrediente)){
            this.estoque.put(ingrediente, 0);
        } else {
            throw new IllegalArgumentException("Ingrediente já cadastrado");
        }
    }

    public void descadastrarIngrediente(Ingrediente ingrediente) {
        if(this.estoque.containsKey(ingrediente)){
            this.estoque.remove(ingrediente);
        } else {
            throw new IllegalArgumentException("Ingrediente não encontrado");
        }
    }

    public void adicionarQuantidadeIngrediente(Ingrediente ingrediente, Integer quantidade) {
        if(this.estoque.containsKey(ingrediente)){
            var quantidadeAtualizada = this.estoque.get(ingrediente) + quantidade;

            validaQuantidade(quantidade);

            this.estoque.replace(ingrediente, quantidadeAtualizada);
        } else {
            throw new IllegalArgumentException("Ingrediente não encontrado");
        }
    }

    public void removerQuantidadeIngrediente(Ingrediente ingrediente, Integer quantidade) {
        if(this.estoque.containsKey(ingrediente)){
            var quantidadeAtualizada = this.estoque.get(ingrediente) - quantidade;

            validaQuantidade(quantidade);

            if(quantidadeAtualizada < 0){
                throw new IllegalArgumentException("Quantidade do ingrediente não pode ser menor que zero");
            } else {
                this.estoque.replace(ingrediente, quantidadeAtualizada);
            }
        } else {
            throw new IllegalArgumentException("Ingrediente não encontrado");
        }
    }

    public void validaQuantidade(Integer quantidade){
        if(quantidade <= 0){
            throw new IllegalArgumentException("Quantidade inválida");
        }
    }

    public Integer consultarQuantidadeEstoque(Ingrediente ingrediente) {
        var quantidadeItem = 0;
        if(this.estoque.containsKey(ingrediente)){
            return this.estoque.get(ingrediente);
        } else {
            throw new IllegalArgumentException(("Ingrediente não encontrado"));
        }
    }
}
