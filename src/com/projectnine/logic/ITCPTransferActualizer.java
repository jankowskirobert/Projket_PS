package com.projectnine.logic;

public interface ITCPTransferActualizer {
	public void setSinglePackageSize(int size);
	public void setTotalPackageSize(long size);
	public void setTotalTime(long seconds);
	public void setSpeed(long seconds);
}
