    package geocodigos.geoconv.model;

    public class PointModel {
        public String id="",registro="", descricao="", latitude="",
                longitude="", norte="", leste="", setorN="",
                setorL="", altitude="", precisao = "";

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

        public String getSetorN() {
            return setorN;
        }

        public void setSetorN(String setorN) {
            this.setorN = setorN;
        }

        public String getSetorL() {
            return setorN;
        }

        public void setSetorL(String setorL) {
            this.setorL = setorL;
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

}
