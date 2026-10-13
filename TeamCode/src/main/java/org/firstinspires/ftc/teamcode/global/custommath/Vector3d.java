package org.firstinspires.ftc.teamcode.global.custommath;

import com.seattlesolvers.solverslib.geometry.Vector2d;

public class Vector3d {
    private double x;
    private double y;
    private double z;

    public Vector3d (Vector2d v, double h){
        this.x=v.getX();
        this.y=v.getY();
        this.z=h;
    }

    public Vector3d (double x, double y, double z){
        this.x=x;
        this.y=y;
        this.z=z;
    }

    public double getX() {return x;}
    public double getY() {return y;}
    public double getZ() {return z;}

    public Vector3d add(Vector3d v){
        return new Vector3d(this.getX() + v.getX(),
                            this.getY() + v.getY(),
                            this.getZ() + v.getZ());
    }

    public Vector3d substract(Vector3d v){
        return new Vector3d(this.getX() - v.getX(),
                            this.getY() - v.getY(),
                            this.getZ() - v.getZ()
        );
    }

    public double dotProduct(Vector3d v){
        return (this.getX() * v.getX()) + (this.getY() * v.getY()) + (this.getZ() * v.getZ());
    }

    public double modulus(){
        return Math.sqrt(this.dotProduct(this));
    }

    public Vector3d scalarMultiplication(double multiplier){
        return new Vector3d(this.getX() * multiplier, this.getY() * multiplier, this.getZ() * multiplier);
    }

    public Vector3d crossProduct(Vector3d v){
        return new Vector3d((this.getY() * v.getZ()) - (this.getZ() * v.getY()),
                            (this.getZ() * v.getX()) - (this.getX() * v.getZ()),
                            (this.getX() * v.getY()) - (this.getY() * v.getX())
        );
    }

    public Vector3d projectedOn(Vector3d v){
        return v.scalarMultiplication(this.dotProduct(v)/v.dotProduct(v));
    }

    public double distanceTo(Vector3d v){
        return this.substract(v).modulus();
    }

    public Vector3d hadamardProduct(Vector3d v){ //element-wise product
        return new Vector3d(this.getX() * v.getX(),
                            this.getY() * v.getY(),
                            this.getY() * v.getZ()
        );
    }

    public Vector2d toVector2d(){
        return new Vector2d(this.getX(), this.getY());
    }
}
