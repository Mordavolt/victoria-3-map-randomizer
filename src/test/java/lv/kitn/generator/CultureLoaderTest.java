package lv.kitn.generator;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.StringReader;
import org.antlr.v4.runtime.CharStreams;
import org.junit.jupiter.api.Test;

final class CultureLoaderTest {
  @Test
  void getCultures() throws Exception {
    var input =
        """
balinese={
	color={ 255 178 102 }
	religion = hindu
	traits = { southeast_asian_heritage malay_culture }
	male_common_first_names = {
		Wayan
		Made
		Nyoman
		Ketut
		Putu
		Kadek
		Komang
		Nengah
		Gede
		Wayan_Balik
	}
	female_common_first_names = {
		Wayan
		Made
		Nyoman
		Ketut
		Putu
		Kadek
		Koming
		Nengah
		Wayan_Balik
	}
	noble_last_names = {
		I_Gusti
		Anak_Agung
		Tjokorda
		I_Dewa
		Ida
		Jero
	}
	common_last_names = {
		Ngakan
		Kompyang
		Sang
		Si
	}
	ethnicities = {
		1 = asian
	}
	graphics = east_asian
}

cajun={
	color= hsv{ 0.62 0.54 0.72 }
	religion = catholic
	traits = { francophone european_heritage }
	male_common_first_names={
		Amos Anatole Aaron Augustin Achille
        Baptiste Bernard Bolivar Blaise Baudouin Branford Barnabe Benjamin
        Charles Clayton Claude Clement Conrad Christophe Constant
        Domenic Dudley David Daniel\s
        Eraste Eugene Earl Edwin Ernoul Emmanuel Etienne
        Felix Frederic Ferdinand Fortunat
        Gustave Gilbert Gabriel Girard
        Henri Herbert Hollis Hardin Hippolyte Hilaire
        Ignace
        Jean Jean-Baptiste Joachim Justin
        Louis Leon Louis-Auguste Luc Lazare Laurent Lionel
        Marius Marc Michel
        Napoleon Nazaire Norbert
        Olivier Omer
        Paul Pierre Pelerin Polycarp
        Raphael Rodrigue
        Sylvain\s
        Thomas Theophile
        Ursin Ulysse Urbain
        Victor Virgil
        Xaxier
	}
	female_common_first_names = {
		Anne Ada Agnes Amelie Angelique Alice
        Blandine Berenice Brigitte Barbara
        Catherine Cecile Constance Christine Claudette Carmen Charlotte
        Drusilla Debora
        Eulalie Evangeline Eurydice
        Flavie Florastine
        Gabrielle Georgette Georgie
        Honorine Hazel
        Jacqueline Jeanne Justine
        Leopauline Louise
        Melanie Modeste Miriam Marie
        Nicole
        Ophelia Odette Olive
        Pelagie Prudence
        Regine Rita Rosette Rose
        Sylvie Seraphine Severine Sophie Sabine
        Theodorine
        Ursuline
        Virginie
        Yolande
	}
	noble_last_names = {
		Bedard
		Bellemare
		Cartier
		David
		Papineau
		Surrette
	}
	common_last_names={
		Arceneaux Aucoin Anzelmo Authement
        Bertinot Bertus Bordelon Brister Broussard Bellocq
        Chachere Chenier de_Clouet Coupar Creaux Cailloux Couvillon
        DeBuys Dede Delaroderie Delhomme Delouche Deshotel Dorgenois Doucet Delaup Dessommes
        Estilette dEnvrich
        Fontenot Freret Fuglaar Flambeau
        Gerois Girod Gisclair Goff Guilbeaux Guyote Grenouille Guillory Guidry Guillotte Glapion\s
        Hebert
        Istre
        Jeanlouis Jearnmard Jumonville
        Lambert Landry Larcade Larriviere Latiolais Lavergne LeBlance Lemelle Lafitte
        Montoucet de_Macarty Marchand Martinet Montegut Mouton Metoyer Morial
        Nicaud Nagin
        Pavy Perrault Pinac Pitot Prieur Peltier Paillard Permillion
        Rabon de_Reggio Rilleux Ritchie Roffignac Roman Roudanez Rozier Robideaux Rosembraise Rillieux
        Savoie Schexnayder Sibille Simien Simoneaux Sarebresole Soule Sentell
        Teekell Teurlings Trevigne Vercher de_Ville Vauquelin Verret Vandage
        Vallencourt
        Wende
        Zeringue
	}
	graphics = european
	ethnicities = {
		1 = caucasian
	}
}
            """;

    var charStream = CharStreams.fromReader(new StringReader(input));
    var cultures = CultureLoader.getCultures(charStream);

    assertThat(cultures).contains(new Culture("cajun"), new Culture("balinese"));
  }
}
