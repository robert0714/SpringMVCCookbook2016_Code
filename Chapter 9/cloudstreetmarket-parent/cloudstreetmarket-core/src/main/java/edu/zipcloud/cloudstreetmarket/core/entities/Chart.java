/***
 *  Cloudstreetmarket.com is a Spring MVC showcase application developed 
 *  with the book Spring MVC Cookbook [PACKT] (2015). 
 * 	Copyright (C) 2015  Alex Bretet
 *  
 *  This program is free software: you can redistribute it and/or modify
 *     it under the terms of the GNU General Public License as published by
 *     the Free Software Foundation, either version 3 of the License, or
 *     (at your option) any later version.
 * 
 *     This program is distributed in the hope that it will be useful,
 *     but WITHOUT ANY WARRANTY; without even the implied warranty of
 *     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *     GNU General Public License for more details.
 * 
 *     You should have received a copy of the GNU General Public License
 *     along with this program.  If not, see <http://www.gnu.org/licenses/>.
 * 
 **/
package edu.zipcloud.cloudstreetmarket.core.entities;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.apache.commons.lang3.time.DateUtils;
import org.springframework.social.yahoo.module.ChartHistoMovingAverage;
import org.springframework.social.yahoo.module.ChartHistoSize;
import org.springframework.social.yahoo.module.ChartHistoTimeSpan;
import org.springframework.social.yahoo.module.ChartType;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "ticker_type")
@Table(name="chart")
public abstract class Chart extends AutoGeneratedId<Long> {

	private static final long serialVersionUID = 115973199499600726L;

	@Enumerated(EnumType.STRING)
	@Column(name="type")
	private ChartType type;
	
	@Enumerated(EnumType.STRING)
	@Column(name="time_span")
	private ChartHistoTimeSpan histoTimeSpan;
	
	@Enumerated(EnumType.STRING)
	@Column(name="moving_average")
	private ChartHistoMovingAverage histoMovingAverage;
	
	@Enumerated(EnumType.STRING)
	@Column(name="size")
	private ChartHistoSize histoSize;
	
	@Column(name="width")
	private Integer intradayWidth;
	
	@Column(name="height")
	private Integer intradayHeight;
	
	@Column(name="internal_path")
	private String internalPath;

	@Column(name="last_update", insertable=false, columnDefinition="TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
	@Temporal(TemporalType.TIMESTAMP)
	private Date lastUpdate;	

	private String path;
	
	public Chart(){	
	}
	
	public Chart(Long id){
		this.id = id;
	}
	
	public Chart(ChartType type, ChartHistoSize histoSize, ChartHistoMovingAverage histoAverage,
			ChartHistoTimeSpan histoPeriod, Integer intradayWidth, Integer intradayHeight, String path) {
		this.type = type;
		this.histoSize = histoSize;
		this.histoMovingAverage = histoAverage;
		this.histoTimeSpan = histoPeriod;
		this.intradayWidth = intradayWidth;
		this.intradayHeight = intradayHeight;
		this.path = path;
	}

	public ChartType getType() {
		return type;
	}

	public void setType(ChartType type) {
		this.type = type;
	}

	public ChartHistoTimeSpan getHistoTimeSpan() {
		return histoTimeSpan;
	}

	public void setHistoTimeSpan(ChartHistoTimeSpan histoTimeSpan) {
		this.histoTimeSpan = histoTimeSpan;
	}

	public ChartHistoMovingAverage getHistoMovingAverage() {
		return histoMovingAverage;
	}

	public void setHistoMovingAverage(ChartHistoMovingAverage histoMovingAverage) {
		this.histoMovingAverage = histoMovingAverage;
	}

	public ChartHistoSize getHistoSize() {
		return histoSize;
	}

	public void setHistoSize(ChartHistoSize histoSize) {
		this.histoSize = histoSize;
	}

	public Integer getIntradayWidth() {
		return intradayWidth;
	}

	public void setIntradayWidth(Integer intradayWidth) {
		this.intradayWidth = intradayWidth;
	}

	public Integer getIntradayHeight() {
		return intradayHeight;
	}

	public void setIntradayHeight(Integer intradayHeight) {
		this.intradayHeight = intradayHeight;
	}

	public String getInternalPath() {
		return internalPath;
	}

	public void setInternalPath(String internalPath) {
		this.internalPath = internalPath;
	}

	public Date getLastUpdate() {
		return lastUpdate;
	}

	public void setLastUpdate(Date lastUpdate) {
		this.lastUpdate = lastUpdate;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public boolean isExpired(int ttlInMinutes){
		Instant now = new Date().toInstant();
		LocalDateTime localNow = now.atZone(ZoneId.systemDefault()).toLocalDateTime();
		LocalDateTime localLastUpdate = DateUtils.addMinutes(lastUpdate, ttlInMinutes).toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
		return localLastUpdate.isBefore(localNow);
	}

	@Override
	public String toString() {
		return "Chart [type=" + type + ", histoTimeSpan=" + histoTimeSpan
				+ ", histoMovingAverage=" + histoMovingAverage + ", histoSize="
				+ histoSize + ", intradayWidth=" + intradayWidth
				+ ", intradayHeight=" + intradayHeight + ", internalPath="
				+ internalPath + ", lastUpdate=" + lastUpdate + ", path="
				+ path + ", id=" + id + "]";
	}
}
