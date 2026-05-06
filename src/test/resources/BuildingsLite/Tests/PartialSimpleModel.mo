within BuildingsLite.Tests;

model PartialSimpleModel

  import Modelica.Units.SI;

  parameter SI.Length h = 0.1;
  parameter SI.Length w = 0.1;
  SI.Area area;
  HeatTransfer.Radiosity.Constant constantRadiosity;


equation

  area = h * w;

end PartialSimpleModel;