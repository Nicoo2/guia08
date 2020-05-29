package frsf.isi.died.guia08.problema01.modelo;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.function.Function;
import java.util.function.Predicate;

import frsf.isi.died.guia08.problema01.util.EmpleadoAsignadoException;
import frsf.isi.died.guia08.problema01.util.TareaFinalizadaException;
import frsf.isi.died.guia08.problema01.util.TareaNoAsignableException;
import frsf.isi.died.guia08.problema01.util.TareaNoEncontradaException;

public class Empleado {

	public enum Tipo {CONTRATADO,EFECTIVO}; 
	
	private Integer cuil;
	private String nombre;
	private Tipo tipo;
	private Double costoHora;
	private List<Tarea> tareasAsignadas;
	
	private Function<Tarea, Double> calculoPagoPorTarea ;
	private Predicate<Tarea> puedeAsignarTarea;

	public Empleado(Integer cuil, String nombre, Tipo tipo, Double costoHora) {
		super();
		this.cuil = cuil;
		this.nombre = nombre;
		this.tipo = tipo;
		this.costoHora = costoHora;
		this.tareasAsignadas = new ArrayList<Tarea>();
		if(tipo == Tipo.CONTRATADO) {
			calculoPagoPorTarea = (Tarea t) -> {
				
				Double costoTarea = (t.getDuracionEstimada() * this.costoHora);
				
				Duration duracion = Duration.between(t.getFechaInicio(), t.getFechaFin()); //duracion
				Integer tiempoRealizacionTarea = (int) (duracion.toDays() * 4); //duracion a dias * 4(horas diarias)
				
				if(tiempoRealizacionTarea < t.getDuracionEstimada()) {
					costoTarea = costoTarea*1.3;
				}
				else if(tiempoRealizacionTarea > (t.getDuracionEstimada()+8)) { // +8hs representa 2 dias laborales extras
					costoTarea *= 0.75;
				}
				
				return costoTarea;
			};
			puedeAsignarTarea = (Tarea t) ->
				(this.tareasAsignadas.stream().findFirst().isEmpty() ||
						this.tareasAsignadas.stream()
		                      .filter(t1->t1.getFechaFin()==null)
		                      .count()<5  );					
		}
		else { //si es EFECTIVO
			calculoPagoPorTarea = (Tarea t) -> {
				
				Double costoTarea = (t.getDuracionEstimada() * costoHora);
				
				Duration duracion = Duration.between(t.getFechaInicio(), t.getFechaFin()); //duracion
				Integer tiempoRealizacionTarea = (int) (duracion.toDays() * 4); //duracion a dias * 4(horas diarias)
				
				if(tiempoRealizacionTarea < t.getDuracionEstimada()) {
					costoTarea = costoTarea*1.2;
				}
				
				return costoTarea;
				
			};
			puedeAsignarTarea = (Tarea t) ->{
				int cantidadHorasPendientes = this.tareasAsignadas.stream()
										.filter(t1 -> t1.getFechaFin() != null)
										.map(t2 -> t2.getDuracionEstimada())
										.reduce(0, (a,b) -> a + b);
				if(cantidadHorasPendientes <= 15) {
					//this.tareasAsignadas.add(t);
					return true;
				}
				else return false;
				}; 
		}	
	}
	public Empleado() {
		// TODO Auto-generated constructor stub
	}
	////
	public Integer getCuil() {
		return cuil;
	}

	public void setCuil(Integer cuil) {
		this.cuil = cuil;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public Tipo getTipo() {
		return tipo;
	}

	public void setTipo(Tipo tipo) {
		this.tipo = tipo;
	}

	public Double getCostoHora() {
		return costoHora;
	}

	public void setCostoHora(Double costoHora) {
		this.costoHora = costoHora;
	}
	
	public List<Tarea> getTareas() {
		return tareasAsignadas;
	}
	
	
	////
	public Double salario() {
		double salarioFinal = this.tareasAsignadas.stream()
							.filter(tarea -> (!tarea.getFacturada()))
							.peek(t -> t.setFacturada(true)) //para marcar como facturadas, REVISAR
							.map(calculoPagoPorTarea)
							.reduce((a,b) -> a + b).get();
		return salarioFinal;
	}
	public Double costoTarea(Tarea t) {
		if(t.getFechaFin() != null) {
			return calculoPagoPorTarea.apply(t);
		}
		else return t.getDuracionEstimada()*costoHora;
		
	}
		
	public Boolean asignarTarea(Tarea t) throws EmpleadoAsignadoException, TareaNoAsignableException, TareaFinalizadaException{ 
		boolean flag;
		if(t.getFechaFin() != null) { //la tarea ya fue finalizada por otro empleado
			flag = false;
			throw new TareaFinalizadaException();
		}
		else if(this.puedeAsignarTarea.test(t)) { 
			if(t.getEmpleadoAsignado() != null) {
				this.tareasAsignadas.add(t);
				flag = true;
			}
			else {
				flag = false;
				throw new EmpleadoAsignadoException(); // hay otro empleado asignado
			}
		}
		else {
			flag = false;
			throw new TareaNoAsignableException(); //no se pudo asignar la tarea
		}
		return flag;
	}
	
	public void comenzar(Integer idTarea) throws TareaNoEncontradaException{
		for(Tarea t : this.tareasAsignadas) {
			if(t.getId() == idTarea) {
				t.setFechaInicio(LocalDateTime.now());
				return;
			}
		}	
		throw new TareaNoEncontradaException();
	}
	
	public void finalizar(Integer idTarea) throws TareaNoEncontradaException{
		for(Tarea t : this.tareasAsignadas) {
			if(t.getId() == idTarea) {
				t.setFechaFin(LocalDateTime.now());
				return;
			}
		}	
		throw new TareaNoEncontradaException();
	}

	public void comenzar(Integer idTarea,String fecha) throws TareaNoEncontradaException {
		for (Tarea t : this.tareasAsignadas) {
			if (t.getId() == idTarea) {
				DateTimeFormatter formato = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
				LocalDateTime parsedFecha = LocalDateTime.parse(fecha, formato);

				t.setFechaInicio(parsedFecha);
				return;
			}
		}
		throw new TareaNoEncontradaException();
	}
	
	public void finalizar(Integer idTarea,String fecha) throws TareaNoEncontradaException{//####### lanzar excepciones
		// busca la tarea en la lista de tareas asignadas 
		// si la tarea no existe lanza una excepción
		// si la tarea existe indica como fecha de finalizacion la fecha y hora actual
		for (Tarea t : this.tareasAsignadas) {
			if (t.getId() == idTarea) {
				DateTimeFormatter formato = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
				LocalDateTime parsedFecha = LocalDateTime.parse(fecha, formato);
				t.setFechaFin(parsedFecha);
				return;
			}
		}
		throw new TareaNoEncontradaException();
	}
}
