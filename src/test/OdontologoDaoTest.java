package test;

import org.junit.jupiter.api.Test;

import java.util.List;
public class OdontologoDaoTest {
    private IDao<Odontologo> odontologoDaoH2;
    private IDao<Odontologo> odontologoDaoMemoria;

    @Before
    public void setUp() {
        odontologoDaoH2 = new OdontologoDaoH2();
        odontologoDaoMemoria = new OdontologoDaoMemoria();
    }

    @Test
    public void testListarTodosH2() {
        Odontologo odontologo1 = new Odontologo(1, "Juan", "Pérez");
        Odontologo odontologo2 = new Odontologo(2, "María", "García");

        odontologoDaoH2.guardar(odontologo1);
        odontologoDaoH2.guardar(odontologo2);

        List<Odontologo> odontologos = odontologoDaoH2.listarTodos();

        Assert.assertFalse(odontologos.isEmpty());
        Assert.assertTrue(odontologos.size() >= 2);
    }

    @Test
    public void testListarTodosMemoria() {
        Odontologo odontologo1 = new Odontologo(1, "Juan", "Pérez");
        Odontologo odontologo2 = new Odontologo(2, "María", "García");

        odontologoDaoMemoria.guardar(odontologo1);
        odontologoDaoMemoria.guardar(odontologo2);

        List<Odontologo> odontologos = odontologoDaoMemoria.listarTodos();

        Assert.assertFalse(odontologos.isEmpty());
        Assert.assertEquals(2, odontologos.size());
    }
}
