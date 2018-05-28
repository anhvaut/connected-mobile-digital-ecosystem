package uow.cmde.transim.util;

import java.awt.Color;
import java.util.StringTokenizer;

/**
 * 
 * @author Vu The Tran
 * @since 18/12/2011
 */
public class ColorHandler {
	
	/**
	 * getRGBString
	 * @param color
	 * @return
	 */
	public static String getRGBString(Color color)
	{
		 String stColor = "" + color.getRed() + "," + color.getGreen() + "," +color.getBlue();
		 
		 return stColor;
	}
	
	/**
	 * getColor
	 * @param stColor
	 * @return
	 */
	public static Color getColor(String stColor)
	{
		Color color = null;
		
		if(stColor.indexOf(",") !=-1)
		{
			//rgb string color
			try
			{
				int i=0;
				int[] colorRGB = new int[3];				
				StringTokenizer stringTokenizer = new  StringTokenizer(stColor,",");

				while(stringTokenizer.hasMoreTokens()){
					
					  colorRGB[i] = Integer.parseInt(stringTokenizer.nextToken().trim());
					  i++;
				}
				
				color = new Color(colorRGB[0],colorRGB[1],colorRGB[2]);
			}
			catch(Exception ex)
			{
				System.out.println(ex.getMessage());
			}
		}
		
		
		return color;
	}
}
