package hellotvxlet;

import java.awt.event.ActionEvent;
import java.io.IOException;
import javax.tv.xlet.*;

import org.bluray.ui.event.HRcEvent;
import org.davic.resources.ResourceClient;
import org.davic.resources.ResourceProxy;
import org.dvb.event.EventManager;
import org.dvb.event.UserEvent;
import org.dvb.event.UserEventListener;
import org.dvb.event.UserEventRepository;
import org.havi.ui.*;
import org.havi.ui.event.*;
import org.dvb.event.*;
import org.havi.ui.event.HBackgroundImageEvent;
import org.havi.ui.event.HBackgroundImageListener;


public class HelloTVXlet implements Xlet, ResourceClient, HBackgroundImageListener,UserEventListener {
    
    //int imagegeladen=0;
    // HBackgroundImage image[]=new HBackgroundImage[4];
    //  HStillImageBackgroundConfiguration hsbconfig;
    
    private HScreen screen;
    private HBackgroundDevice bgDevice;
    private HBackgroundConfigTemplate bgTemplate;
    private HStillImageBackgroundConfiguration bgConfiguration;
    private int imagegeladen=0;
    private HStaticText tekst;
    private HScene scene;
    private HBackgroundImage[] image=new HBackgroundImage [7]; //("doggo's")
    private int huidig;
    
    public void initXlet(XletContext ctx) throws XletStateChangeException//code p51-52
    {
        screen=HScreen.getDefaultHScreen();
        bgDevice=screen.getDefaultHBackgroundDevice(); 
        if (bgDevice.reserveDevice(this))  //niet oplossen met bolletje
        {
            System.out.println("Achtergrond device succesvol gereserveerd");
        }
        HBackgroundConfigTemplate bgTemplate=new HBackgroundConfigTemplate();
        bgTemplate.setPreference(HBackgroundConfigTemplate.STILL_IMAGE, HBackgroundConfigTemplate.REQUIRED);
         bgConfiguration=(HStillImageBackgroundConfiguration) bgDevice.getBestConfiguration(bgTemplate);
    //     ^-----"globale" variable 
        try {
            bgDevice.setBackgroundConfiguration(bgConfiguration);
        } catch (java.lang.Exception e) {
            System.out.println(e.toString());
        }
       image[0]=new HBackgroundImage("dog_fifi.png");
       image[1]=new HBackgroundImage("dog_luna.png");
       image[2]=new HBackgroundImage("dog_penny.png");
       image[3]=new HBackgroundImage("dog_pinky.png");
       image[4]=new HBackgroundImage("dog_rambo.png");
       image[5]=new HBackgroundImage("dog_twix.png");
       image[6]=new HBackgroundImage("dog_zthankyou.png");
       // bovenaan  HBackgroundImage image[]=new HBackgroundImage[4];
       
  //    ^-----"globale" variable
        // C:\Program Files\TechnoTrend\TT-MHP-Browser\fileio\DSMCC\0.0.3
        for(int i=0;i<7;i++)
        { 
            image[i].load(this); 
        }
       // interface HBackgroundImageListener toevoegen aan Xlet
       
       UserEventRepository repo = new UserEventRepository ("naam");
       repo.addAllArrowKeys();
       EventManager.getInstance().addUserEventListener((UserEventListener) this,repo);
       scene = HSceneFactory.getInstance().getDefaultHScene();
        tekst = new HStaticText("Donatie: \n", 380, 200, 500, 200);
        tekst.setHorizontalAlignment(HVisible.VALIGN_TOP);
        
        
        
        scene.add(tekst);
        scene.validate();
        scene.setVisible(true);
       
    } 
    
    
    
    public void userEventReceived(UserEvent e){
        if (e.getType()==HRcEvent.KEY_PRESSED){
            if (e.getCode()==HRcEvent.VK_RIGHT){
                huidig++;
                if (huidig>6) huidig=1;
            }
            if (e.getCode()==HRcEvent.VK_LEFT){
                huidig--;
                if (huidig<1) huidig=6;
            }
            if (e.getCode()==HRcEvent.VK_ENTER){
                System.out.println("ENTER");
                
                String dog="dog\n";
                if (huidig==0) dog="Fifi\n";
                if (huidig==1) dog="Fifi\n";
                if (huidig==2) dog="Luna\n";
                if (huidig==3) dog="Penny\n";
                if (huidig==4) dog="Pinky\n";
                if (huidig==5) dog="Rambo\n";
                if (huidig==6) dog="Twix\n";
                tekst.setTextContent(tekst.getTextContent(HVisible.NORMAL_STATE)+ dog, HVisible.NORMAL_STATE);
                          
            }
            
            try {
                bgConfiguration.displayImage(image[huidig-1]);
            }  
            catch (Exception ex){ex.printStackTrace();} {
                //System.out.println(ex);
            }
            
            if (e.getCode()==HRcEvent.VK_DOWN){
                //tekst.setTextContent(" ",HState.NORMAL_STATE);
                try {
                    bgConfiguration.displayImage(image[6]);
                } catch (IOException ex) {
                    ex.printStackTrace();
                } catch (HPermissionDeniedException ex) {
                    ex.printStackTrace();
                } catch (HConfigurationException ex) {
                    ex.printStackTrace();
                }
                tekst.setTextContent(" ",HState.NORMAL_STATE);
           }
        
           
                
            
            
  
       
        }
    }
    
    public void imageLoaded(HBackgroundImageEvent e) {
       System.out.println("Image succesvol geladen");
       imagegeladen++;
     if (imagegeladen==5)
     {
       try {
            bgConfiguration.displayImage(image[0]);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
     }
    }
    public void pauseXlet() {
       
    }

    public void startXlet() throws XletStateChangeException {
        EventManager manager = EventManager.getInstance();
        UserEventRepository repository = new UserEventRepository("Voorbeeld");
        
        repository.addKey(org.havi.ui.event.HRcEvent.VK_LEFT);
        repository.addKey(org.havi.ui.event.HRcEvent.VK_RIGHT);
        repository.addKey(org.havi.ui.event.HRcEvent.VK_DOWN);
        repository.addKey(org.havi.ui.event.HRcEvent.VK_UP);
        repository.addKey(org.havi.ui.event.HRcEvent.VK_ENTER);
        
        manager.addUserEventListener(this, repository);
    }

    public boolean requestRelease(ResourceProxy proxy, Object requestData) {
       return false;
    }

    public void release(ResourceProxy proxy) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void notifyRelease(ResourceProxy proxy) {
        throw new UnsupportedOperationException("Not supported yet.");
    }


    public void imageLoadFailed(HBackgroundImageEvent e) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

  
    public void destroyXlet(boolean unconditional) throws XletStateChangeException {
      
    }

    private HStaticText setTextContent(int NORMAL_STATE) {
        throw new UnsupportedOperationException("Not yet implemented");
    }

    

}