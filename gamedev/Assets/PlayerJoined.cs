using System.Collections;
using System.Collections.Generic;
using UnityEngine;

public class PlayerJoined : Bolt.EntityBehaviour<IcustomCubeState>
{
    // Start is called before the first frame update
    public override void Attached()
    {
        var e = PlayerjoinedEvent.Create();
        e.Message = "What's up Sluts";
        e.Send();
    }
}
