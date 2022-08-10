package pedido;

import ingredientes.Ingrediente;
import produto.Shake;
import produto.TipoTamanho;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Optional;

public class Pedido{

    private int id;
    private  ArrayList<ItemPedido> itens;
    private Cliente cliente;

    public Pedido(int id, ArrayList<ItemPedido> itens,Cliente cliente){
        this.id = id;
        this.itens=itens;
        this.cliente=cliente;
    }

    public ArrayList<ItemPedido> getItens() {
        return itens;
    }

    public int getId(){
        return this.id;
    }

    public Cliente getCliente(){
        return this.cliente;
    }

    public double calcularTotal(Cardapio cardapio){
        double total = 0;

        for (ItemPedido itemPedido : itens) {
            total += itemPedido.getShake().getTipoTamanho().atualizaValorTamanho(cardapio.buscarPreco(itemPedido.getShake().getBase())) * itemPedido.getQuantidade();
            for (Ingrediente adicional : itemPedido.getShake().getAdicionais()) {
                total += cardapio.buscarPreco(adicional) * itemPedido.getQuantidade();
            }
        };

        return total;
    }

    public void adicionarItemPedido(ItemPedido itemPedidoAdicionado){
        if(itemPedidoAdicionado.getShake().getAdicionais() != null){
            Collections.sort(itemPedidoAdicionado.getShake().getAdicionais(), Collections.reverseOrder());
        }
        Optional<ItemPedido> itemPedido = itens.stream().filter(f -> f.getShake().equals(itemPedidoAdicionado.getShake())).findFirst();
        if(itemPedido.isEmpty()){
            itens.add(itemPedidoAdicionado);
        } else {
            itemPedidoAdicionado.setQuantidade(itemPedido.get().getQuantidade() + itemPedidoAdicionado.getQuantidade());
            itens.remove(itemPedido.get());
            itens.add(itemPedidoAdicionado);
        }
    }

    public void removeItemPedido(ItemPedido itemPedidoRemovido) {
        if(itemPedidoRemovido.getShake().getAdicionais() != null){
            Collections.sort(itemPedidoRemovido.getShake().getAdicionais(), Collections.reverseOrder());
        }
        Optional<ItemPedido> itemPedido = itens.stream().filter(f -> f.getShake().equals(itemPedidoRemovido.getShake())).findAny();
        if (itemPedido.isEmpty()) {
            throw new IllegalArgumentException("Item nao existe no pedido.");
        } else {
            if(itemPedido.get().getQuantidade() - 1 == 0){
                itens.remove(itemPedido.get());
            } else {
                for (ItemPedido i : itens) {
                    if(i.equals(itemPedido.get())){
                        i.setQuantidade(i.getQuantidade() - 1);
                    }
                }
            }
        }
    }

    @Override
    public String toString() {
        return this.itens + " - " + this.cliente;
    }
}
