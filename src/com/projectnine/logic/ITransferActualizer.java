package com.projectnine.logic;

public interface ITransferActualizer {
	public void setSinglePackageSize(int size);
	public void setTotalPackageSize(double size);
	public void setTotalTime(double seconds);
	public void setSpeed(double seconds);
	public void updateStatus(ServerStatus status);
	public void resetView();
}
