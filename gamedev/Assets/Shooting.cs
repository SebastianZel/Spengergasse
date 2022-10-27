using System;
using System.Collections;
using System.Collections.Generic;
using UnityEngine;

public class Shooting : Bolt.EntityBehaviour<IcustomCubeState>
{
    public Rigidbody bulletprefab;
    public float bulletSpeed = 6;
    public GameObject muzzle;


    public override void Attached()
    {
        state.OnShoot = Shoot;
            ;
    }

    private void Update()
    {
        if (Input.GetKeyDown(KeyCode.Space) && entity.IsOwner)
        {
            state.Shoot();
        }
    }

    private void Shoot()
    {
        Rigidbody rigi = Instantiate(bulletprefab,muzzle.transform.position, this.transform.rotation);

        rigi.velocity = transform.TransformDirection(new Vector3(0, 0, bulletSpeed));
    }
}
