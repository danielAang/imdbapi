package com.dan.imdbapi.dto;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

public class ExhibitionDate {

	@JsonFormat(pattern = "dd/MM/yyyy HH:mm")
	private Date exhibition;

	public ExhibitionDate(Date exhibition) {
		super();
		this.exhibition = exhibition;
	}

	public Date getExhibition() {
		return exhibition;
	}

	public void setExhibition(Date exhibition) {
		this.exhibition = exhibition;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((exhibition == null) ? 0 : exhibition.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ExhibitionDate other = (ExhibitionDate) obj;
		if (exhibition == null) {
			if (other.exhibition != null)
				return false;
		} else if (!exhibition.equals(other.exhibition))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "ExhibitionDate [exhibition=" + exhibition + "]";
	}

}
