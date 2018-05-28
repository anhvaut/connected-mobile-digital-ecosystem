package uow.cmde.transim.transit.model.impl;

import uow.cmde.transim.transit.model.*;

public class Schedule implements ISchedule{
	private int id;
	
	public int getId()
	{
		return id;
	}
	public void setId(int id)
	{
		this.id = id;
	}
}
