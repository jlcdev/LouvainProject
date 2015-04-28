package Optional;

import com.google.gson.Gson;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Javier López Calderón
 */
public class LoadProperties
{
    public Properties loadJsonProperties(String filePath)
    {
        File f = new File(filePath);
        if(f.isDirectory() || !f.exists()) return null;
        BufferedReader br = null;
        try
        {
            br = new BufferedReader(new FileReader(filePath));
            byte[] encoded = Files.readAllBytes(Paths.get(filePath));
            String data = new String(encoded, StandardCharsets.UTF_8);
            Gson g = new Gson();
            Properties p = g.fromJson(data, Properties.class);
            return p;
        }
        catch (Exception ex)
        {
            Logger.getLogger(LoadProperties.class.getName()).log(Level.SEVERE, null, ex);
        }
        finally
        {
            try
            {
                br.close();
            } catch (IOException ex)
            {
                Logger.getLogger(LoadProperties.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return null;
    }
}
