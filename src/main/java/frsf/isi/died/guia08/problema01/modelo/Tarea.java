package frsf.isi.died.guia08.problema01.modelo;

import java.time.LocalDateTime;

import frsf.isi.died.guia08.problema01.util.EmpleadoAsignadoException;
import frsf.isi.died.guia08.problema01.util.TareaFinalizadaException;

public class Tarea {

	private Integer id;
	private String descripcion;
	private Integer duracionEstimada;
	private Empleado empleadoAsignado;
	private LocalDateTime fechaInicio;
	private LocalDateTime fechaFin;
	private Boolean facturada;
	
	public void asignarEmpleado(Empleado e) throws TareaFinalizadaException, EmpleadoAsignadoException {
		if(this.fechaFin != null) {
			throw new TareaFinalizadaException();
		}
		if(this.empleadoAsignado != null) {
			throw new EmpleadoAsignadoException();
		}	
		this.empleadoAsignado = e;	
	}

	public Tarea(Integer id, String descripcion, Integer duracionEstimada) {
		super();
		this.id = id;
		this.descripcion = descripcion;
		this.duracionEstimada = duracionEstimada;
		this.facturada = false;
	}



	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public Integer getDuracionEstimada() {
		return duracionEstimada;
	}

	public void setDuracionEstimada(Integer duracionEstimada) {
		this.duracionEstimada = duracionEstimada;
	}

	public LocalDateTime getFechaInicio() {
		return fechaInicio;
	}

	public void setFechaInicio(LocalDateTime fechaInicio) {
		this.fechaInicio = fechaInicio;
	}

	public LocalDateTime getFechaFin() {
		return fechaFin;
	}

	public void setFechaFin(LocalDateTime fechaFin) {
		this.fechaFin = fechaFin;
	}

	public Boolean getFacturada() {
		return facturada;
	}

	public void setFacturada(Boolean facturada) {
		this.facturada = facturada;
	}

	public Empleado getEmpleadoAsignado() {
		return empleadoAsignado;
	}
	
	public String asCsv() {
		return this.id+ ";\""+ this.descripcion+"\";"+this.facturada +";\"" +this.getFechaInicio() +";\""+ this.getFechaFin()+";\""
				+ this.getEmpleadoAsignado().getCuil()+";\"" + this.empleadoAsignado.getNombre() +";\"" ;
		}
	
}
