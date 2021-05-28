<%
response.setHeader("Content-Encoding", "UTF-8");
response.setContentType("text/csv; charset=UTF-8");
response.setHeader("Content-Disposition","attachment; filename=ParticipAct_Example_CSV_Importar.csv");
%>
"name","surname","email","password","birthdate","currentAddress","currentCity","currentZipCode","currentNumber","currentProvince","currentCountry","mapLat","mapLng","contactPhoneNumber","device","notes"
"Fulano","Santos","fulano@example.com","password","2016-01-01","Avenida Madre Benvenuta - Trindade","Florianopolis","88034000","S/N","Santa Catarina","Brasil","-27.6058242","-48.5975519,13","(48) 3664-8200","Nexus X","Aluno Regular"
"Beltrano","Oliveira","beltrano@example.com","password","2016-01-01","Avenida Madre Benvenuta - Trindade","Florianopolis","88034000","1","Santa Catarina","Brasil","-27.6058242","-48.5975519,13","(48) 3664-8200","Android Z","Comunidade"
"Ciclano","Souza","ciclano@example.com","password","2016-01-01","Avenida Madre Benvenuta - Trindade","Florianopolis","88034000","500","Santa Catarina","Brasil","-27.6058242","-48.5975519,13","(48) 3664-8200","Ubuntu U","Observações adicionais"