using System.Collections;
using System.Collections.Generic;
using UnityEngine;

public class ServerEventListener : Bolt.GlobalEventListener
{
    public override void OnEvent(PlayerjoinedEvent evnt)
    {
        Debug.Log(evnt.Message);
    }
}
