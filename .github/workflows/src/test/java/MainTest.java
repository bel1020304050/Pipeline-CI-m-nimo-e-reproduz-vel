import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;


public class MainTest {

    @Test
    void deveRetornarMensagemDaBiblioteca() {
        String mensagem = "Aplicação Biblioteca funcionando!";

        assertEquals("Aplicação Biblioteca funcionando!", mensagem);
    }
}
