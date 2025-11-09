/*
Code taken from : 
https://www.newthinktank.com/2012/09/adapter-design-pattern-tutorial/
*/

import java.util.*;

public class ExampleCodeTester{

   public static void main(String[] agrs){
      //making tank objects
      EnemyTank tank1 = new EnemyTank();
      EnemyAttacker tank2 = new EnemyTank();
      
      //making an object of type EnemyRobot
      EnemyRobot robot1 = new EnemyRobot();
      
      //need to use the adapter to interact with EnemyRobot
      EnemyRobotAdapter robot1Attacker = new EnemyRobotAdapter(robot1);
      
      //a collection of attackers
      List<EnemyAttacker> attackers = new ArrayList<>();
      
      //adding attackers to the collection
      attackers.add(tank1);
      attackers.add(tank2);
      attackers.add(robot1Attacker);
      
      //create an iterator and go over the collection
      Iterator itr = attackers.iterator();
      
      while(itr.hasNext()){
         EnemyAttacker temp = (EnemyAttacker)itr.next();
         
         //attack
         temp.fireWeapon();
         
         //move the attacker
         temp.driveForward();
         
         System.out.println();
      }
   }

}