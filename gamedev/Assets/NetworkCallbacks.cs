using System.Collections;
using System.Collections.Generic;
using UnityEngine;
using Bolt;


public class NetworkCallbacks : GlobalEventListener
{
    public GameObject cubePrefab;

    public override void SceneLoadLocalDone(string scene)
    {
        var spawnPos = new Vector3(Random.Range(-10,10),0,Random.Range(-10,10));
        BoltNetwork.Instantiate(cubePrefab, spawnPos, Quaternion.identity);
    }

}
