package frsf.isi.died.guia08.problema01;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import frsf.isi.died.guia08.problema01.modelo.Empleado;
import frsf.isi.died.guia08.problema01.modelo.Tarea;
import frsf.isi.died.guia08.problema01.util.EmpleadoAsignadoException;
import frsf.isi.died.guia08.problema01.util.TareaFinalizadaException;
import frsf.isi.died.guia08.problema01.util.TareaNoAsignableException;
import frsf.isi.died.guia08.problema01.modelo.Empleado.Tipo;

public class AppRRHHTest {

	Empleado e1,e2,e3;
	Tarea t1,t2,t3,t4;
	AppRRHH aplicacion;
	@Before
	public void init(){
		
		aplicacion = new AppRRHH();
		
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
	public void testAgregarEmpleadoContratado() {
		
		aplicacion.agregarEmpleadoContratado(202, "Nicolas Springer", 12.7);
		Empleado aux = aplicacion.getEmpleados().get(0);
		if(	(aux.getCostoHora() == 12.7)
			&&(aux.getNombre() == "Nicolas Springer")
			&&(aux.getCuil() == 202)) {
			assertTrue(true);
		}
		else {
			assertTrue(false);
		}
	}
	
	@Test
	public void testAgregarEmpleadoEfectivo() {
		
		aplicacion.agregarEmpleadoEfectivo(204, "Nicolas Springer", 25.1);
		Empleado aux = aplicacion.getEmpleados().get(0);
		if(	(aux.getCostoHora() == 25.1)
			&&(aux.getNombre() == "Nicolas Springer")
			&&(aux.getCuil() == 204)) {
			assertTrue(true);
		}
		else {
			assertTrue(false);
		}
	}

	@Test
	public void testAsignarTarea() { //no funciona el predicato -> devuele No value presente el .get()
		aplicacion.getEmpleados().add(e1);
		aplicacion.asignarTarea(e1.getCuil(), 23, "PruebaAsignar", 10);
			if(e1.getTareas().get(0).getId() == 23) {
				assertTrue(true);
			}
			else {
				assertTrue(false);
			}
	}
		
	@Test
	public void testEmpezarTarea() { 	
		aplicacion.getEmpleados().add(e1);
		aplicacion.asignarTarea(e1.getCuil(), 23, "PruebaAsignar", 10);
		aplicacion.empezarTarea(e1.getCuil(), 23);
		Empleado aux = aplicacion.getEmpleados().get(0);
		if(aux.getTareas().get(0).getFechaInicio() != null) {
			assertTrue(true);
		}
		else {
			assertTrue(false);
		}	
	}
	
	@Test
	public void testTerminarTarea() { 	
		aplicacion.getEmpleados().add(e1);
		aplicacion.asignarTarea(e1.getCuil(), 23, "PruebaAsignar", 10);
		aplicacion.terminarTarea(e1.getCuil(), 23);
		Empleado aux = aplicacion.getEmpleados().get(0);
		if(aux.getTareas().get(0).getFechaFin() != null) {
			assertTrue(true);
		}
		else {
			assertTrue(false);
		}	
	}
	
	//test de archivos
	
}
