package frsf.isi.died.guia08.problema01.modelo;

import static org.junit.Assert.*;

import org.junit.Test;

import frsf.isi.died.guia08.problema01.modelo.Empleado.Tipo;
import frsf.isi.died.guia08.problema01.util.EmpleadoAsignadoException;
import frsf.isi.died.guia08.problema01.util.TareaFinalizadaException;

public class TareaTest {

	@Test 
	public void asignarEmpleadoTest() {
		
		Tarea t1 = new Tarea(1, "Prueba", 15);
		Empleado e1 = new Empleado(101, "Nicolas", Tipo.EFECTIVO, 15.0);
		
		try {
			t1.asignarEmpleado(e1);
			assertTrue(true);
		} catch (TareaFinalizadaException e) {
			assertTrue(true);
		} catch (EmpleadoAsignadoException e) {
			assertTrue(true);
		}
		
		
	}

}
