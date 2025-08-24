package RoboLucao;

import robocode.*;
import robocode.util.Utils;
import java.awt.Color;
import java.util.Random;

public class RoboLucao extends AdvancedRobot {
    private Random random = new Random();

    public void run() {
        setColors(Color.green, Color.black, Color.gray); // body, gun, radar

        setTurnRadarRight(Double.POSITIVE_INFINITY); 
        
        while (true) {
            setAhead(200); 
            execute(); 
        }
    }

    public void onScannedRobot(ScannedRobotEvent e) {
        double distance = e.getDistance();
        double firePower;
		
		double enemyVelocity = e.getVelocity();
		double enemyEnergy = e.getEnergy();
		
        if (distance < 100) {
            firePower = 3;
        } else if (distance < 300) {
            firePower = 2;
        } else {
            firePower = 1;
        }

		//direção exata do inimigo
        double absoluteBearing = getHeading() + e.getBearing();
		
		// alinhar o canhão na direção do inimigo
       	double gunTurn = Utils.normalRelativeAngleDegrees(absoluteBearing - getGunHeading());
		
		if(enemyVelocity <= 0){		
       		firePower = 2;
		}
		
        setTurnGunRight(gunTurn);
        fire(firePower); 
		
        // alinhar com o inimigo
        double radarTurn = Utils.normalRelativeAngleDegrees(absoluteBearing - getRadarHeading());
		
	   	// se for negativo - antihorario, positivo - horario
        setTurnRadarRight(radarTurn + (radarTurn < 0 ? -20 : 20)); 
        execute();
		
        if (distance < 300) {
            setTurnRight(90);
            setBack(100);
        }
    }

    public void onHitByBullet(HitByBulletEvent e) {
        double distance = random.nextInt(200) + 20;
        double angle = random.nextInt(360);

        setTurnRight(angle);
        setBack(distance);
        execute();
    }

    public void onHitWall(HitWallEvent e) {
        setTurnLeft(90);
        setAhead(100); 
		 setTurnRadarRight(360); 
        execute();
    }
}