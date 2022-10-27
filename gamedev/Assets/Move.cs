using System.Collections;
using System.Collections.Generic;
using UnityEngine;
using Bolt;
using UdpKit;
using Bolt.Matchmaking;
using System;
using UdpKit.Platform.Photon;

public class Move : GlobalEventListener
{
   

    public void StartServer() { BoltLauncher.StartServer(); }


    public override void BoltStartDone()
    {
        BoltMatchmaking.CreateSession(sessionID: "test", sceneToLoad: "Game");
    }

    
    public void Startclient()
    {
        BoltLauncher.StartClient();
    }

    public override void SessionListUpdated(Map<Guid, UdpSession> sessionList)
    {
        foreach(var s in sessionList)
        {
            UdpSession udpSession = s.Value as UdpSession;
            if(udpSession.Source == UdpSessionSource.Photon)
            {
                BoltMatchmaking.JoinSession(udpSession);
            }
        }

        base.SessionListUpdated(sessionList);
    }
}
