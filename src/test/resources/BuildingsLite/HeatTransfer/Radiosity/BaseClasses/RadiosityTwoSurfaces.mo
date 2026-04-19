within BuildingsLite.HeatTransfer.Radiosity.BaseClasses;
partial block RadiosityTwoSurfaces
  "Model for the radiosity balance of a device with two surfaces"
  extends BuildingsLite.BaseClasses.BaseIcon;

  parameter Modelica.Units.SI.Area A "Heat transfer area";

  BuildingsLite.HeatTransfer.Interfaces.RadiosityInflow JIn_a(start=A*0.8*Modelica.Constants.sigma*293.15^4)
    "Incoming radiosity at surface a"
    annotation (Placement(transformation(extent={{-120,30},{-100,50}})));
  BuildingsLite.HeatTransfer.Interfaces.RadiosityInflow JIn_b(start=A*0.8*Modelica.Constants.sigma*293.15^4)
    "Incoming radiosity at surface b"
    annotation (Placement(transformation(extent={{120,-50},{100,-30}})));
  BuildingsLite.HeatTransfer.Interfaces.RadiosityOutflow JOut_a
    "Outgoing radiosity at surface a"
    annotation (Placement(transformation(extent={{-100,-50},{-120,-30}})));
  BuildingsLite.HeatTransfer.Interfaces.RadiosityOutflow JOut_b
    "Outgoing radiosity at surface b"
    annotation (Placement(transformation(extent={{100,30},{120,50}})));

  annotation (           Documentation(info="<html>
Partial model for a device with two surfaces.
</html>", revisions="<html>
<ul>
<li>
February 3, by Michael Wetter:<br/>
Corrected bug in start value of radiosity port.
</li>
<li>
August 19 2010, by Michael Wetter:<br/>
First implementation.
</li>
</ul>
</html>"));
end RadiosityTwoSurfaces;
