package armazem;

import ingredientes.Base;
import ingredientes.TipoBase;
import ingredientes.TipoTopping;
import ingredientes.Topping;
import org.junit.jupiter.api.*;

import java.util.TreeMap;

import static org.junit.jupiter.api.Assertions.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class ArmazemTest {

    Armazem armazem;

    @BeforeAll
    void setup(){
        armazem = new Armazem();
        armazem.cadastrarIngredienteEmEstoque(new Base(TipoBase.SORVETE));
        armazem.cadastrarIngredienteEmEstoque(new Topping(TipoTopping.AVEIA));
    }

    @Test
    @DisplayName("Teste: Adicionar um ingrediente não existente no estoque")
    public void teste_adicionar_ingrediente_armazem() {
        Base base = new Base(TipoBase.IORGUTE);

        armazem.cadastrarIngredienteEmEstoque(base);

        assertTrue(armazem.getEstoque().containsKey(base));
    }

    @Test
    @DisplayName("Teste: Adicionar um ingrediente existente no estoque")
    public void teste_adicionar_ingrediente_existente(){
        Base base = new Base(TipoBase.SORVETE);

        try {
            this.armazem.cadastrarIngredienteEmEstoque(base);
            fail("Excecao nao encontrada");
        } catch (Throwable e) {
            assertEquals("Ingrediente já cadastrado", e.getMessage());
            assertEquals(IllegalArgumentException.class, e.getClass());
        }
    }

    @Test
    @DisplayName("Teste: Descadastrar ingrediente existente")
    public void teste_descadastrar_ingrediente_existente(){
        Base base = new Base(TipoBase.LEITE);
        armazem.cadastrarIngredienteEmEstoque(base);

        armazem.descadastrarIngrediente(base);

        assertFalse(armazem.getEstoque().containsKey(base));
    }

    @Test
    @DisplayName("Teste: Descadastrar ingrediente não existente")
    public void teste_descadastrar_ingrediente_nao_existente(){
        Base baseIorgute = new Base(TipoBase.IORGUTE);

        try{
            armazem.descadastrarIngrediente(baseIorgute);
        } catch (Throwable e) {
            assertEquals("Ingrediente não encontrado", e.getMessage());
            assertEquals(IllegalArgumentException.class, e.getClass());
        }
    }

    @Test
    @DisplayName("Teste: Adicionar quantiade de um ingrediente")
    public void teste_adicionar_quantidade_ingrediente_estoque(){
        Topping topping = new Topping(TipoTopping.AVEIA);

        armazem.adicionarQuantidadeIngrediente(topping, 4);

        assertEquals(armazem.getEstoque().get(topping), 9);
    }

    @Test
    @DisplayName("Teste: Adicionar quantiade de um ingrediente inexistente")
    public void teste_adicionar_quantidade_ingrediente_inexistente_estoque(){
        Topping topping = new Topping(TipoTopping.MEL);

        try{
            armazem.adicionarQuantidadeIngrediente(topping, 2);
            fail("Exceção não encontrada");
        } catch (Throwable e){
            assertEquals("Ingrediente não encontrado", e.getMessage());
            assertEquals(IllegalArgumentException.class, e.getClass());
        }
    }

    @Test
    @DisplayName("Teste: Adicionar quantidade menor que 0")
    public void teste_adicionar_quantidade_ingrediente_menor_que_zero_estoque(){
        Topping topping = new Topping(TipoTopping.AVEIA);

        try{
            armazem.adicionarQuantidadeIngrediente(topping, -2);
            fail("Exceção não encontrada");
        } catch (Throwable e){
            assertEquals("Quantidade inválida", e.getMessage());
            assertEquals(IllegalArgumentException.class, e.getClass());
        }
    }

    @Test
    @DisplayName("Teste: Remover quantiade de um ingrediente")
    public void teste_remover_quantidade_ingrediente_estoque(){
        Topping topping = new Topping(TipoTopping.CHOCOLATE);
        armazem.cadastrarIngredienteEmEstoque(topping);
        armazem.adicionarQuantidadeIngrediente(topping, 4);

        armazem.removerQuantidadeIngrediente(topping, 2);

        assertEquals(armazem.getEstoque().get(topping), 2);
    }

    @Test
    @DisplayName("Teste: Remover quantiade de um ingrediente inexistente")
    public void teste_remover_quantidade_ingrediente_inexistente_estoque(){
        Topping topping = new Topping(TipoTopping.MEL);

        try{
            armazem.removerQuantidadeIngrediente(topping, 2);
            fail("Exceção não encontrada");
        } catch (Throwable e){
            assertEquals("Ingrediente não encontrado", e.getMessage());
            assertEquals(IllegalArgumentException.class, e.getClass());
        }
    }

    @Test
    @DisplayName("Teste: Remover quantidade menor que 0")
    public void teste_remover_quantidade_ingrediente_menor_que_zero_estoque(){
        Topping topping = new Topping(TipoTopping.AVEIA);

        try{
            armazem.removerQuantidadeIngrediente(topping, -2);
            fail("Exceção não encontrada");
        } catch (Throwable e){
            assertEquals("Quantidade inválida", e.getMessage());
            assertEquals(IllegalArgumentException.class, e.getClass());
        }
    }

    @Test
    @DisplayName("Teste: Remover quantidade a um valor menor que zero")
    public void teste_remover_quantidade_ingrediente_a_um_valor_menor_que_zero_estoque(){
        Topping topping = new Topping(TipoTopping.AVEIA);

        try{
            armazem.removerQuantidadeIngrediente(topping, 10);
            fail("Exceção não encontrada");
        } catch (Throwable e){
            assertEquals("Quantidade do ingrediente não pode ser menor que zero", e.getMessage());
            assertEquals(IllegalArgumentException.class, e.getClass());
        }
    }

    @Test
    @DisplayName("Teste: Consultar quantidade em estoque")
    public void teste_consultar_quantidade_estoque(){
        Topping topping = new Topping(TipoTopping.AVEIA);
        armazem.adicionarQuantidadeIngrediente(topping,5);

        var quantaidadeItem = armazem.consultarQuantidadeEstoque(topping);

        assertEquals(quantaidadeItem, 5);
    }

    @Test
    @DisplayName("Teste: Consultar quantidade em estoque de item inexistente")
    public void teste_consultar_quantidade_item_inexistente_estoque(){
        Topping topping = new Topping(TipoTopping.MEL);

        try{
            var quantaidadeItem = armazem.consultarQuantidadeEstoque(topping);
            fail("Exceção não encontrada");
        } catch (Throwable e){
            assertEquals("Ingrediente não encontrado", e.getMessage());
            assertEquals(IllegalArgumentException.class, e.getClass());
        }
    }


}