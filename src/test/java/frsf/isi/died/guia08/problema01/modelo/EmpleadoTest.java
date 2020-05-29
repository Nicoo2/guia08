package frsf.isi.died.guia08.problema01.modelo;

import static org.junit.Assert.*;

import java.time.LocalDateTime;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import frsf.isi.died.guia08.problema01.modelo.Empleado.Tipo;
import frsf.isi.died.guia08.problema01.util.EmpleadoAsignadoException;
import frsf.isi.died.guia08.problema01.util.TareaFinalizadaException;
import frsf.isi.died.guia08.problema01.util.TareaNoAsignableException;
import frsf.isi.died.guia08.problema01.util.TareaNoEncontradaException;

public class EmpleadoTest {

	// IMPORTANTE
	// ESTA CLASE ESTA ANOTADA COMO @IGNORE por lo que no ejecutará ningun test
	// hasta que no borre esa anotación.
	
	Empleado e1,e2,e3;
	Tarea t1,t2,t3,t4;
	
	@Before
	public void init(){
		e1 = new Empleado(101, "Nico", Tipo.CONTRATADO, 25.0);
		e2 = new Empleado(102, "Juan", Tipo.EFECTIVO, 30.0);
		e3 = new Empleado(103, "Esteban", Tipo.CONTRATADO, 55.0);
		 
		 t1 = new Tarea(1, "a", 15);
		 t2 = new Tarea(2, "b", 16);
		 t3 = new Tarea(3, "c", 17);
		 t4 = new Tarea(4, "d", 18);

//			try {
//				e1.asignarTarea(t1);
//				e1.asignarTarea(t2);
//				e2.asignarTarea(t3);
//				e2.asignarTarea(t4);
//			} catch (EmpleadoAsignadoException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			} catch (TareaNoAsignableException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			} catch (TareaFinalizadaException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
			

	}
	
	
	@Test
	public void testAsignarTarea() {
	
		try {
			boolean asigno = e1.asignarTarea(t1);
			assertTrue(asigno);
		} catch (EmpleadoAsignadoException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (TareaNoAsignableException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (TareaFinalizadaException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	
	@Test
	public void testSalario() {
		
		try {
			e1.asignarTarea(t1);
			e1.asignarTarea(t2);

		
			e1.comenzar(1, "2000-03-24 9:33");
			e1.finalizar(1, "2000-03-25 10:33");
			
			
			Double salarioEmpleado = e1.salario();
			
			Double esperado = 775.0;
			
			assertEquals(esperado, salarioEmpleado);
		} catch (EmpleadoAsignadoException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (TareaNoAsignableException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (TareaFinalizadaException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
//		catch (TareaNoEncontradaException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
		catch (TareaNoEncontradaException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	
	}
	
	@Test
	public void testCostoTarea() {

	}


	@Test
	public void testComenzarInteger() {

		try {
			e1.comenzar(1);
			if(e1.getTareas().get(0).getFechaInicio() != null) {
				assertTrue(true);
			}
			else {
				assertTrue(false);
			}
			
		} catch (TareaNoEncontradaException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	@Test
	public void testFinalizarInteger() {
		
		try {
			e1.finalizar(1);
			if(e1.getTareas().get(0).getFechaFin() != null) {
				assertTrue(true);
			}
			else {
				assertTrue(false);
			}
			
		} catch (TareaNoEncontradaException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}

	@Test
	public void testComenzarIntegerString() {
		
		try {
			e1.comenzar(1 ,"2000-03-03 10:33" );
			if(e1.getTareas().get(0).getFechaInicio() != null) {
				assertTrue(true);
			}
			else {
				assertTrue(false);
			}
			
		} catch (TareaNoEncontradaException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}

	@Test
	public void testFinalizarIntegerString() {
		try {
			e1.comenzar(1 ,"2000-03-17 10:33" );
			if(e1.getTareas().get(0).getFechaInicio() != null) {
				assertTrue(true);
			}
			else {
				assertTrue(false);
			}
		} catch (TareaNoEncontradaException e) {
			e.printStackTrace();
		}
	}

}
