package br.com.bergmannsoft.config;

/**
 * Variaveis e constantes compartilhadas no projeto
 *
 * @author Claudio
 */
public class Config {
    // ####################### ####################### #######################
    // ####################### PROJETO
    public static final int PRODUCTION_PORT = 8080;// PROD
    public static final String PRODUCTION_LANG = "pt_BR";
    public static final String PRODUCTION_GEO_LAT = "-27.586347";
    public static final String PRODUCTION_GEO_LNG = "-48.502900";
    public static final String PRODUCTION_URL = "http://painel.participact.com.br/";
    //public static final String PRODUCTION_HOST = "/v2/";
    public static final String PRODUCTION_HOST = "http://localhost:8080/participact-server/";
    public static final String PRODUCTION_RESOURCES = "/resources";
    public static final String PRODUCTION_RESOURCES_FILES = PRODUCTION_RESOURCES + "/public/file";
    public static final String PRODUCTION_RESOURCES_IMGS = PRODUCTION_RESOURCES + "/public/img";
    public static final String PRODUCTION_RESOURCES_DIR = "/var/lib/tomcat7/webapps/participact-server/public/file"; //<---- PROD

    // ####################### ####################### #######################
    // ####################### PYTHON Scripts
    public static final String PYTHON_SCRIPT_BIN = "/usr/bin/python3.5";// <---- PROD
    //    public static final String PYTHON_SCRIPT_BIN = "python";//<---- LOCALHOST
    public static final String PYTHON_SCRIPT_PATH = "/home/participact/participact-python/";// <---- PROD
    //    public static final String PYTHON_SCRIPT_PATH = "C:\\Workspace\\htdocs\\Projetos\\participact-pyfix\\";//<---- LOCALHOST
    public static final String PYTHON_SCRIPT_IMPORT = PYTHON_SCRIPT_BIN + " " + PYTHON_SCRIPT_PATH + "main_import_file.py -i %s -s %s -p %s";// <---- PROD
    public static final String PYTHON_SCRIPT_CKAN_EXPORT = "/usr/bin/python2.7 " + PYTHON_SCRIPT_PATH + "main_ckan_comcap_export.py -s %s -e %s -q %s -p %s";// <---- PROD
    public static final String PYTHON_SCRIPT_CELESC_EXPORT = "/usr/bin/python2.7 " + PYTHON_SCRIPT_PATH + "main_ckan_celesc_export.py -s %s -e %s -q %s -p %s";// <---- PROD
    public static final String PYTHON_SCRIPT_EXCEL_EXPORT = "/usr/bin/python2.7 " + " " + PYTHON_SCRIPT_PATH + "main_excel_export.py -p '%s' -u %s >> " + PYTHON_SCRIPT_PATH + "/tmp/EXCEL.log";// <---- PROD
    // ####################### ####################### #######################
    // ####################### FFMPEG
    public static final String FFMPEG_SCRIPT_BIN = "/usr/local/bin/ffmpeg"; // <--- PROD
    // public static final String FFMPEG_SCRIPT_BIN = "ffmpeg"; // <--- LOCALHOST
    public static final String FFMPEG_CMD_MP3 = FFMPEG_SCRIPT_BIN + " -y -i %s -vn -ar 44100 -ac 2 -ab 192k -f mp3 %s0";
    public static final String FFMPEG_CMD_AAC = FFMPEG_SCRIPT_BIN + " -y -i %s -acodec copy %s";
    public static final String FFMPEG_CMD_MP4 = FFMPEG_SCRIPT_BIN + " -y -i %s -codec:v libx264 -codec:a libfdk_aac -crf 18 -movflags faststart -metadata mimetype=video/mp4 %s";
    public static final String FFMPEG_CMD_H264 = FFMPEG_SCRIPT_BIN + " -an -y -i %s -vcodec libx264 -pix_fmt yuv420p -profile:v baseline -level 3 %s";
    public static final String FFMPEG_CMD_WAV = FFMPEG_SCRIPT_BIN + " -y -i %s -acodec pcm_u8 -ar 22050 %s";
    public static final String FFMPEG_CMD_FLAC = FFMPEG_SCRIPT_BIN + " -y -i %s -f flac 22050 %s";
    // ####################### ####################### #######################
    // ####################### ARQUIVOS
//    public static final String PRODUCTION_STORAGE_TEMP = "/tmp/"; // <---- PROD
    public static final String PRODUCTION_STORAGE_TEMP = "/temp/"; //<---- LOCALHOST
    public static final String PRODUCTION_STORAGE_MEDIA = "/var/www/vhosts/participact-media-service/"; // <---- PROD
    public static final String PRODUCTION_STORAGE_MEDIA_URL = "http://media.participact.com.br"; // <---- PROD
    public static final String PRODUCTION_STORAGE_ACCOUNTNAME = "CONFIDENTIAL_INFORMATION";
    public static final String PRODUCTION_STORAGE_ACCOUNTKEY = "CONFIDENTIAL_INFORMATION";
    public static final String PRODUCTION_STORAGE_CONTAINER = "participact-container";
    public static final String PRODUCTION_STORAGE_ENDPOINTSUFFIX = "blob.core.windows.net";
    public static final String PRODUCTION_STORAGE_ENDPOINTPROTOCOL = "https";
    public static final String PRODUCTION_STORAGE_ENDPOINTCONNECTION = "DefaultEndpointsProtocol=" + PRODUCTION_STORAGE_ENDPOINTPROTOCOL + ";AccountName=" + PRODUCTION_STORAGE_ACCOUNTNAME + ";AccountKey=" + PRODUCTION_STORAGE_ACCOUNTKEY;
    public static final String PRODUCTION_STORAGE_URL = PRODUCTION_STORAGE_ENDPOINTPROTOCOL + "://" + PRODUCTION_STORAGE_ACCOUNTNAME + "." + PRODUCTION_STORAGE_ENDPOINTSUFFIX + "/" + PRODUCTION_STORAGE_CONTAINER;
    // ####################### ####################### #######################
    // ####################### ARQUIVOS
    public static final String PRODUCTION_MEDIA_TENANT = "CONFIDENTIAL_INFORMATION.onmicrosoft.com";
    public static final String PRODUCTION_MEDIA_CLIENT_ID = "CONFIDENTIAL_INFORMATION";
    public static final String PRODUCTION_MEDIA_CLIENT_KEY = "CONFIDENTIAL_INFORMATION";
    public static final String PRODUCTION_MEDIA_ENDPOINT = "https://participact.restv2.brazilsouth.media.azure.net/api/";
    public static final String PRODUCTION_MEDIA_REST = "https://rest.media.azure.net";
    public static final String PRODUCTION_MEDIA_PREFERED_ENCODER = "Media Encoder Standard";
    public static final String PRODUCTION_MEDIA_ENCODING_PRESET = "Adaptive Streaming";
    // ####################### ####################### #######################
    // ####################### DATABASE
    public static final String DATABASE_HOST = "jdbc:postgresql://127.0.0.1:5432/participact_db"; // Default
    public static final String DATABASE_USER = "postgres";
    public static final String DATABASE_PASS = "postgres";
    // ####################### ####################### #######################
    // ####################### REDES SOCIAIS
    public static final String[] allowedSocialNetworks = {"FACEBOOK", "GOOGLE"};
    // ####################### ####################### #######################
    // ####################### GOOGLE
    public static final String GOOGLE_API_KEY_MAP = "CONFIDENTIAL_INFORMATION";
    public static final String GOOGLE_APP_PROJECT_ID = "participact-brasil";
    public static final String GOOGLE_APP_PROJECT_NUMBER = "CONFIDENTIAL_INFORMATION";
    public static final String GOOGLE_APP_CLIENT_SECRET = "CONFIDENTIAL_INFORMATION";

    public static final String GOOGLE_PLUS_PROJECT_ID = "participactbrasil-1100";
    public static final String GOOGLE_PLUS_PROJECT_NUMBER = "CONFIDENTIAL_INFORMATION";
    public static final String GOOGLE_PLUS_CLIENT_SECRET = "CONFIDENTIAL_INFORMATION";
    // ####################### FACEBOOK
    public static final String FACEBOOK_CLIENT_ID = "CONFIDENTIAL_INFORMATION";
    public static final String FACEBOOK_CLIENT_SECRET = "CONFIDENTIAL_INFORMATION";
    public static final String[] FACEBOOK_CLIENT_FIELDS = {"id", "name", "email", "link", "age_range", "gender", "locale", "picture"};
    // ####################### ####################### #######################
    // ####################### QR CODE
    public static final String QRCODEIMAGE = "[[QRCODEIMAGE]]";
    public static final String QRCODETOKEN = "[[QRCODETOKEN]]";
    public static final String QRCODEURL = "[[QRCODEURL]]";
    // ####################### ####################### #######################
    // ####################### EMAIL
    public static final String emailHostName = "smtp.gmail.com";
    public static final int emailSmtpPort = 465;
    public static final String emailAuthenticatorUsername = "CONFIDENTIAL_INFORMATION@gmail.com";
    public static final String emailAuthenticatorPassword = "CONFIDENTIAL_INFORMATION";
    public static final boolean emailSSLOnConnect = true;
    public static final String emailFrom = "CONFIDENTIAL_INFORMATION@gmail.com";
    public static final String emailQrCodeImage = "<img src=\"https://chart.googleapis.com/chart?chs=200&cht=qr&chl=participactbr://token/" + QRCODETOKEN + "\" width=\"200\">";
    public static final String emailQrcodeUrl = "participactbr://token/" + QRCODETOKEN;
    // ####################### ####################### #######################
    // ####################### QUERY
    public static final int SELECT_MIN_OFFSET = 1;
    public static final int SELECT_MAX_OFFSET = 1000;
    public static final int SELECT_MIN_COUNT = 1;
    public static final int SELECT_MAX_COUNT = 100000;
    public static final int SELECT_DEFAULT_OFFSET = 1;
    public static final int SELECT_DEFAULT_COUNT = 10;
    // ####################### ####################### #######################
    // ####################### PAGES
    public static final String PRODUCTION_PAGE_PRIVACY_ = PRODUCTION_HOST + "pages/privacy";
    public static final String PRODUCTION_PAGE_LICENSE = PRODUCTION_HOST + "pages/license";
    public static final String PRODUCTION_PAGE_FAQ = PRODUCTION_HOST + "pages/faq";
    public static final String PRODUCTION_PAGE_ABOUT = PRODUCTION_HOST + "pages/about";
    public static final String PRODUCTION_PAGE_IMG = PRODUCTION_HOST + "pages/file";
    // ####################### ####################### #######################
    // ####################### PARTICIPANTES / DEFAULT
    public static final String defaultUserZipCode = "88000";
    public static final String defaultUserPhoneNumber = "(48)3664-8000";
    public static final String defaultUserProjectPhoneNumber = "(48)3664-8000";
    public static final String defaultUserUniDepartment = " ";
    public static final String defaultUserUniDegree = "N/A";
    public static final String defaultUserEmail = "contato@participact.com.br";
    public static final String defaultUserPassword = "participact";
    public static final String defaultAlink = "javascript:;";
    public static final Long defaultLimitSending = 500L;
    public static final Long defaultLimitSendingMax = 2000L;
    public static final Long defaultLimitTimeMax = 604800L;
    public static final boolean emailDebug = false;
    public static final boolean emailEnable = true;
    // ####################### ####################### #######################
    // ####################### CGU DADOS
//     Homologacao / localhost
//    public static final Integer CGU_CLIENT_ID = 18;
//    public static final String CGU_CLIENT_SECRET = "CONFIDENTIAL_INFORMATION";
//    public static final String CGU_GRANT_TYPE = "password";
//    public static final String CGU_USERNAME = "webservice_part_trein";
//    public static final String CGU_PASSWORD = "CONFIDENTIAL_INFORMATION";
    // Produção
    public static final Integer CGU_CLIENT_ID = 36;
    public static final String CGU_CLIENT_SECRET = "CONFIDENTIAL_INFORMATION";
    public static final String CGU_GRANT_TYPE = "password";
    public static final String CGU_USERNAME = "webservice_part_pro";
    public static final String CGU_PASSWORD = "CONFIDENTIAL_INFORMATION";
    // Protocolo
    public static final String CGU_PROTOCOL_REST = "json";
    public static final String CGU_PROTOCOL_SOAP = "soap";
    public static final String CGU_PROTOCOL_DEFAULT = CGU_PROTOCOL_REST;
    public static final Boolean CGU_ALLOW_ALL_REPORT = true; // Se True, todos os usuarios podem postar relatorios, se false, somente os liberados
    // ####################### CGU REST
    // Homologacao
//    public static final String CGU_REST_URL = "treinamentoouvidorias.cgu.gov.br";
    // Producao
     public static final String CGU_REST_URL = "sistema.ouvidorias.gov.br";
    // APIs
    public static final String CGU_REST_GET_TOKEN = "oauth/token";
    public static final String CGU_REST_GET_OUVIDORIAS = "api/ouvidorias?somenteOuvidoriasAtivas=1";
    public static final String CGU_REST_GET_ORGAOS_SIORG = "api/orgaos-siorg";
    public static final String CGU_REST_POST_CADASTRA_MANIFESTACAO = "api/manifestacoes";
    public static final String CGU_REST_GET_CONSULTA_MANIFESTACAO = "api/manifestacoes?DataAtualizacaoInicio=%s&dataAtualizacaoFim=%s&idSituacaoManifestacao=%d";
    public static final String CGU_REST_GET_DETALHA_MANIFESTACAO = "api/manifestacoes/%d";
    // ####################### CGU SOAP
    public static final String CGU_SOAP_CREDENTIALS = "<ser:login>" + CGU_USERNAME + "</ser:login><ser:senha>" + CGU_PASSWORD + "</ser:senha>";
    public static final String GCU_SOAP_ENVELOPE = "<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:ser=\"%s\"><soapenv:Header/><soapenv:Body>%s</soapenv:Body></soapenv:Envelope>";
    // ####################### CGU SOAP / ServicoConsultaDadosCodigos.svc
    public static final String GCU_SOAP_URL_CONSULTA_DADOS_CODIGOS = "http://treinamentoouvidorias.cgu.gov.br/Servicos/ServicoConsultaDadosCodigos.svc/v1";
    public static final String GCU_SOAP_SERV_CONSULTA_DADOS_CODIGOS = "http://sistema.ouvidorias.gov.br/servicos/ServicoConsultaDadosCodigos:v1";
    public static final String GCU_SOAP_ACTION_CONSULTA_DADOS_CODIGOS = GCU_SOAP_SERV_CONSULTA_DADOS_CODIGOS + "/IServicoConsultaDadosCodigos/%s";
    // ####################### CGU SOAP / ServicoOuvidorias.svc
    public static final String GCU_SOAP_URL_OUVIDORIAS = "http://treinamentoouvidorias.cgu.gov.br/Servicos/ServicoOuvidorias.svc/v4";
    public static final String GCU_SOAP_SERV_OUVIDORIAS = "http://sistema.ouvidorias.gov.br/servicos/ServicoOuvidorias:v4";
    public static final String GCU_SOAP_ACTION_OUVIDORIAs = GCU_SOAP_SERV_OUVIDORIAS + "/IServicoOuvidorias/%s";
    // ####################### CGU SOAP / ServicoManterManifestacao.svc
    public static final String GCU_SOAP_URL_MANIFESTACAO = "http://treinamentoouvidorias.cgu.gov.br/Servicos/ServicoManterManifestacao.svc/v5";
    public static final String GCU_SOAP_SERV_MANIFESTACAO = "http://sistema.ouvidorias.gov.br/servicos/ServicoManterManifestacao:v5";
    public static final String GCU_SOAP_ACTION_MANIFESTACAO = GCU_SOAP_SERV_MANIFESTACAO + "/ServicoManterManifestacao/%s";
    // ####################### CGU SOAP / ServicoConsultaManifestacao.svc
    public static final String GCU_SOAP_URL_CONSULTA_MANIFESTACAO = "http://treinamentoouvidorias.cgu.gov.br/Servicos/ServicoConsultaManifestacao.svc/v3";
    public static final String GCU_SOAP_SERV_CONSULTA_MANIFESTACAO = "http://sistema.ouvidorias.gov.br/servicos/ServicoConsultaManifestacao:v3";
    public static final String GCU_SOAP_ACTION_CONSULTA_MANIFESTACAO = GCU_SOAP_SERV_CONSULTA_MANIFESTACAO + "/IServicoConsultaManifestacao/%s";
    // ####################### ####################### #######################
    // ####################### GEO LOCATION
    public static final double GEO_LOCATION_R = 6371; // earth radius in km
    public static final double GEO_LOCATION_RADIUS = 50; // km
    public static final double GEO_LOCATION_RADIUS_LOW = 5; // km
    public static Long defaultLimitTime = 86500L;
    // ####################### ####################### #######################
    // ####################### NOMINATIM
    public static final String NOMINATIM_SERVER_URL = "https://nominatim.openstreetmap.org/reverse?lat=%s&lon=%s&format=json";
    public static final String NOMINATIM_HEADER_EMAIL = emailFrom;
}
