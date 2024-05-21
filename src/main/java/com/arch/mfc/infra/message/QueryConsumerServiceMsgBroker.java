package com.arch.mfc.infra.message;

import com.arch.mfc.infra.inputport.CQRSMessageBrokerInputPort;
import com.arch.mfc.infra.utils.ConversionUtils;
import org.apache.poi.ss.formula.functions.T;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class QueryConsumerServiceMsgBroker {

    @Autowired
    CQRSMessageBrokerInputPort messageBrokerInputPort;

   public void consumeEvent( @Payload( required = false ) String eventMsg ) {
        if ( eventMsg == null ) {
            return;
        }
        Map<String, Object> event = ConversionUtils.jsonstring2Map( eventMsg );

        Map<String, Object> payload = (Map<String, Object>) event.get("payload");
        String operation = (String) payload.get("op");
        String table = (String) payload.get("table");

        if ( operation.equals("u") ) {
            messageBrokerInputPort.updateReg(table, (Map<String, Object>) payload.get("after"));
        } else if ( operation.equals("c") ) {
            messageBrokerInputPort.insertReg(table, (Map<String, Object>) payload.get("after"));
        } else if ( operation.equals("d") ) {
            messageBrokerInputPort.deleteReg(table, (Map<String, Object>) payload.get("before"));
        } 
    }
    
}
