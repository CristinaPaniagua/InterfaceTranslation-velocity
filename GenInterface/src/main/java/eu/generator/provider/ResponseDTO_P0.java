// Auto generated
package eu.generator.provider;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.lang.Override;
import java.lang.String;

@JsonIgnoreProperties(
    ignoreUnknown = true
)
public class ResponseDTO_P0 {
  private String n;

  private float v;

  private String u;

  public ResponseDTO_P0() {
  }

  public ResponseDTO_P0(String n, float v, String u) {
    this.n = n;
    this.v = v;
    this.u = u;
  }

  @Override
  public String toString() {
    return "ProviderPayload{" + "n=" + n+ ",  "+ "v=" + v+ ",  "+ "u=" + u+ ",  " +"}";
  }

  public String getn() {
    return n;
  }

  public void setn(String n) {
    this.n=n;
  }

  public float getv() {
    return v;
  }

  public void setv(float v) {
    this.v=v;
  }

  public String getu() {
    return u;
  }

  public void setu(String u) {
    this.u=u;
  }
}
