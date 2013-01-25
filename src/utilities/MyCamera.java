package utilities;

import edu.wpi.first.wpilibj.camera.*;
import edu.wpi.first.wpilibj.camera.AxisCamera.ResolutionT;
import edu.wpi.first.wpilibj.image.*;

public class MyCamera {

    // CONSTANTS
    final int m_iBrightness = 0;
    final int m_iCompression = 0;
    final double m_dDistanceConst = 0;
    final ResolutionT m_rResolution = ResolutionT.k320x240;
    
    double m_dX = 0;
    double m_dY = 0;
    double m_dDistance;
    AxisCamera camera;
    ColorImage image = null;
    BinaryImage binaryImage = null;
    ParticleAnalysisReport[] report;
    ParticleAnalysisReport m_rFinal;
    
    public MyCamera() {
        camera = camera.getInstance();
        camera.writeBrightness(m_iBrightness);
//	  camera.writeColorLevel(Vars.color);
        camera.writeCompression(m_iCompression);
        camera.writeResolution(m_rResolution);
        camera.getInstance();
    }

    public void run() 
    {
        image = null;
        report = null;
        
        try 
        {
            if (camera.freshImage()) 
            {
                image = camera.getImage();
                // TODO: Fix this!!!
                binaryImage = image.thresholdRGB(0, 127, 100, 255, 0, 127);
                
                if(binaryImage.getNumberParticles() > 0)
                {
                    report = binaryImage.getOrderedParticleAnalysisReports(1);
                    m_rFinal = report[0];
                    
                    m_dX = m_rFinal.center_mass_x;
                    m_dY = m_rFinal.center_mass_y;
                    m_dDistance = calcDistance(m_rFinal);
                }
                
                else
                {
                    m_dX = Vars.dCameraCenterX;
                    m_dY = Vars.dCameraCenterY;
                    m_dDistance = 0;
                }
            }
        } 

        catch (NIVisionException exception) 
        {
            exception.printStackTrace();
        } 

        catch (AxisCameraException exception) 
        {
            exception.printStackTrace();
        }
        
        finally 
        {
            try
            {
                if(image != null)
                    image.free();

                if(binaryImage != null)
                    binaryImage.free();                
            }
            
            catch(NIVisionException exception){} 
        } 
    }
    
    public double getPicX()
    {
        return m_dX;
    }
    
    public double getPicY()
    {
        return m_dY;
    }
    
    public double getDistance()
    {
        return m_dDistance;
    }
    
    private double calcDistance(ParticleAnalysisReport rArg)
    {
        return m_dDistanceConst / (rArg.boundingRectWidth);
    }
}