    package geocodigos.geoconv.model;

    public class PointModel {
        private String id="",registro="", descricao="", latitude="",
                longitude="", norte="", leste="", setor="",
                altitude="", precisao = "",
                data="", hora="", selecionado="";

        public String getId() {
            return id;
        }

        public void setId(String id){
            this.id=id;
        }

        public String getRegistro() {
            return registro;
        }

        public void setRegistro(String registro) {
            this.registro = registro;
        }

        public String getDescricao() {
            return descricao;
        }

        public void setDescricao(String descricao) {
            this.descricao= descricao;
        }

        public String getlatitude() {
            return latitude;
        }

        public void setLatidude(String latitude) {
            this.latitude = latitude;
        }

        public String getLongitude() {
            return longitude;
        }

        public void setLongitude(String longitude) {
            this.longitude= longitude;
        }

        public String getNorte(){
            return norte;
        }

        public void setNorte(String norte) {
            this.norte=norte;
        }

        public String getLeste() {
            return leste;
        }

        public void setLeste(String leste) {
            this.leste=leste;
        }

        public String getSetor() {
            return setor;
        }

        public void setSetor(String setor) {
            this.setor = setor;
        }

        public String getAltitude() {
            return altitude;
        }

        public void setAltitude(String altitude) {
            this.altitude=altitude;
        }

        public String getPrecisao() {
            return precisao;
        }

        public void setPrecisao(String precisao) {
            this.precisao = precisao;
        }

        public String getData() { return data; }

        public void setData(String data) {
            this.data=data;
        }

        public String getHora() {return hora; }

        public void setHora(String hora) {
            this.hora = hora;
        }

        public String getSelecao(){return selecionado;}

        public void setSelecao(String selecionado) {
            this.selecionado=selecionado;
        }

}
