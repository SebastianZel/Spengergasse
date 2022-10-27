using System.Collections;
using System.Collections.Generic;
using UnityEngine;

public class PlayerHealth : Bolt.EntityBehaviour<IcustomCubeState>
{
    public int localhealth = 3;


    // Start is called before the first frame update

    public override void Attached()
    {
        state.Health = localhealth;

        state.AddCallback("Health",HealthCallback);
    }


    private void HealthCallback()
    {

        localhealth = state.Health;

        if (localhealth <= 0)
        {
            BoltNetwork.Destroy(gameObject);
        }

    }

    // Update is called once per frame
    void Update()
    {
        if (Input.GetKeyDown(KeyCode.H))
        {
            state.Health -= 1;
        }

        
    }
}
