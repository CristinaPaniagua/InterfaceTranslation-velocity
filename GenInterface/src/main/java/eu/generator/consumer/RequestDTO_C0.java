// Auto generated
package eu.generator.consumer;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.lang.Override;
import java.lang.String;

@JsonIgnoreProperties(
    ignoreUnknown = true
)
public class RequestDTO_C0 {
  private String name;

  private String localization;

  private Value value;

  public RequestDTO_C0() {
  }

  public RequestDTO_C0(String name, String localization, Value value) {
    this.name = name;
    this.localization = localization;
    this.value = value;
  }

  @Override
  public String toString() {
    return "ProviderPayload{" + "name=" + name+ ",  "+ "localization=" + localization+ ",  "+ "value=" + value +"}";
  }

  public String getname() {
    return name;
  }

  public void setname(String name) {
    this.name=name;
  }

  public String getlocalization() {
    return localization;
  }

  public void setlocalization(String localization) {
    this.localization=localization;
  }

  public Value getvalue() {
    return value;
  }

  public void setvalue(Value value) {
    this.value=value;
  }
}
