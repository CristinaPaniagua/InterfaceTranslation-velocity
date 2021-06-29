// Auto generated
package eu.generator.resources;

import eu.generator.consumer.RequestDTO_C0;
import eu.generator.consumer.ResponseDTO_C0;
import eu.generator.provider.RequestDTO_P0;
import eu.generator.provider.ResponseDTO_P0;
import java.lang.String;

public class PayloadTranslator {
  public PayloadTranslator() {
  }

  public RequestDTO_P0 requestAdaptor(RequestDTO_C0 payload_C) {
     RequestDTO_P0 payload_P = new RequestDTO_P0();
    String name=payload_C.getname() ;
     String name_P=name;
    payload_P.setn(name_P);
    int temp=payload_C.getvalue().gettemp() ;
    float temp_P= temp;
    payload_P.setv(temp_P);
    String unit=payload_C.getvalue().getunit() ;
     String unit_P=unit;
    payload_P.setu(unit_P);
    return payload_P;
  }

  public ResponseDTO_C0 responseAdaptor(ResponseDTO_P0 payload_P) {
     ResponseDTO_C0 payload_C= new ResponseDTO_C0();
    String n=payload_P.getn() ;
     String n_C=n;
    payload_C.setname(n_C);
    float v=payload_P.getv() ;
    int v_C=(int)v;
     eu.generator.consumer.Value value = new  eu.generator.consumer.Value ();
     value.settemp(v_C);
    payload_C.setvalue(value);
    String u=payload_P.getu() ;
     String u_C=u;
     value.setunit(u_C);
    payload_C.setvalue(value);
    return payload_C;
  }
}
