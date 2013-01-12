package utilities;

import edu.wpi.first.wpilibj.camera.AxisCamera;
//import edu.wpi.first.wpilibj.camera.AxisCamera.ResolutionT;
import edu.wpi.first.wpilibj.image.BinaryImage;
import edu.wpi.first.wpilibj.image.ColorImage;
import edu.wpi.first.wpilibj.image.ParticleAnalysisReport;
/**
 *
 * @author Fauzi
 */
// TODO: Set constants down below
public class MyCamera {
    	
    // CONSTANTS
    private static final int m_iBrightness = 0;
    private static final int m_iColorLevel = 0;
    private static final int m_dCompression = 0;
    
    private AxisCamera camera = null;
    private ColorImage image = null;
    private BinaryImage binaryImage = null;
    private ParticleAnalysisReport[] report = null;

    public MyCamera() {
            camera = camera.getInstance();
            camera.writeBrightness(m_iBrightness);
            camera.writeColorLevel(m_iColorLevel);
            camera.writeCompression(m_dCompression);
            camera.writeExposureControl(AxisCamera.ExposureT.hold);
            camera.writeExposurePriority(AxisCamera.ExposurePriorityT.none);
            camera.writeMaxFPS(m_iBrightness);
            camera.writeResolution(AxisCamera.ResolutionT.k160x120);
            camera.writeRotation(AxisCamera.RotationT.k0);
            camera.writeWhiteBalance(AxisCamera.WhiteBalanceT.automatic);
            camera.getInstance();
    }
    
    
}
