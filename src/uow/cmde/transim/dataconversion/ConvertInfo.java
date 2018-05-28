package uow.cmde.transim.dataconversion;

public class ConvertInfo {
	
	Object firstParameter;
	Object secondParameter;
	public ConvertInfo(Object firstParameter, Object secondParameter)
	{
		this.firstParameter = firstParameter;
		this.secondParameter = secondParameter;
	}
	
	public Object getFirstParameter(){
		return firstParameter;
	}
	
	public Object getSecondParameter(){
		return secondParameter;
	}
	
	public void setFirstParameter(Object firstParameter){
		this.firstParameter = firstParameter;
	}
	
	public void setSecondParameter(Object secondParameter){
		this.secondParameter = firstParameter;
	}
}
