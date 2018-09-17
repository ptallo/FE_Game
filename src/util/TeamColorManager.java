package util;

import javafx.scene.image.Image;
import javafx.scene.image.PixelReader;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
import javafx.scene.paint.Color;

public class TeamColorManager {

    private Color[] team1Colors = new Color[]{
            Color.rgb(88, 149, 31), // light
            Color.rgb(74, 114, 36), // med
            Color.rgb(63, 92, 36)   // dark
    };

    private Color[] team2Colors = {
            Color.rgb(215, 80, 65),
            Color.rgb(173, 53, 40),
            Color.rgb(153, 46, 36)
    };

    private Color[] team3Colors = {
            Color.rgb(55, 102, 210),
            Color.rgb(31, 87, 171),
            Color.rgb(39, 67, 133)
    };

    private Color[][] teams = {team1Colors, team2Colors, team3Colors};

    public Image convertToTeam(Image image, int teamIndex) {
        int imageTeamIndex = getTeam(image);
        if (imageTeamIndex == teamIndex || imageTeamIndex == -1) {
            return image;
        }

        WritableImage writableImage = new WritableImage(
                (int) image.getWidth(),
                (int) image.getHeight()
        );

        PixelWriter writer = writableImage.getPixelWriter();
        for (int y = 0; y < image.getHeight(); y++) {
            for (int x = 0; x < image.getWidth(); x++) {
                Color color = image.getPixelReader().getColor(x, y);

                boolean writtenTo = false;
                for (int i = 0; i < teams[imageTeamIndex].length; i++) {
                    if (color.equals(teams[imageTeamIndex][i])) {
                        Color newColor = teams[teamIndex][i];
                        writer.setColor(x, y, newColor);
                        writtenTo = true;
                    }
                }

                if (!writtenTo) {
                    writer.setColor(x, y, color);
                }

            }
        }

        return writableImage;
    }

    private int getTeam(Image image) {
        PixelReader pixelReader = image.getPixelReader();
        for (int y = 0; y < image.getHeight(); y++) {
            for (int x = 0; x < image.getWidth(); x++) {
                Color color = pixelReader.getColor(x, y);
                for (int i = 0; i < teams.length; i++) {
                    if (colorInArray(teams[i], color)) {
                        return i;
                    }
                }
            }
        }
        return -1;
    }

    private boolean colorInArray(Color[] array, Color color) {
        for (Color iColor : array) {
            if (iColor.equals(color)) {
                return true;
            }
        }
        return false;
    }
}
