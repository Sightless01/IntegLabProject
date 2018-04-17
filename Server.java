package server;

import shared.*;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.rmi.registry.Registry;
import java.rmi.registry.LocateRegistry;
import java.util.ArrayList;

public class Server {
    public static void main(String[] args) {
        ArrayList<Person> members = null;
        ArrayList<Project> projects = null;
        try {
            FileInputStream memberIn = new FileInputStream("members.obj");
            ObjectInputStream memIn = new ObjectInputStream(memberIn);
            members = (ArrayList<Person>) memIn.readObject();
            FileInputStream fileIn = new FileInputStream("projects.obj");
            ObjectInputStream in = new ObjectInputStream(fileIn);
            projects = (ArrayList<Project>) in.readObject();
            memIn.close();
            memberIn.close();
            in.close();
            fileIn.close();
            Module module = new ModuleImplementation(members, projects);
            Registry registry = LocateRegistry.getRegistry();
            registry.rebind("server", module);
        } catch (IOException i) {
            i.printStackTrace();
        } catch (ClassNotFoundException c) {
            System.out.println("Employee class not found");
            c.printStackTrace();
        } catch(Exception e) {
            e.printStackTrace();
        }
    }
}