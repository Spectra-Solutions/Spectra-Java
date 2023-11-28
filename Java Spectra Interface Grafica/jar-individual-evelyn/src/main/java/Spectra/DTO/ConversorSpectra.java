package Spectra.DTO;

import java.util.concurrent.TimeUnit;

public class ConversorSpectra {
    public static String formatarBytes(long bytes) {
        if (bytes == 1L) {
            return String.format("%d byte", bytes);
        }

        else if (bytes < 1024L) {
            return String.format("%d bytes", bytes);
        }

        else if (bytes < 1048576L) {
            return formatarUnidades(bytes, 1024L, ".");
        }

        else if (bytes < 1073741824L) {
            return formatarUnidades(bytes, 1048576L, ".");
        }

        else if (bytes < 1099511627776L) {
            return formatarUnidades(bytes, 1073741824L, ".");
        }

        else if (bytes < 1125899906842624L) {
            return formatarUnidades(bytes, 1099511627776L, ".");
        }

        else {
            return bytes < 1152921504606846976L ? formatarUnidades(bytes, 1125899906842624L, ".") : formatarUnidades(bytes, 1152921504606846976L, ".");
        }
    }

    public static String formatarSegundosDecorridos(long secs) {
        long days = TimeUnit.SECONDS.toDays(secs);
        long eTime = secs - TimeUnit.DAYS.toSeconds(days);
        long hr = TimeUnit.SECONDS.toHours(eTime);
        eTime -= TimeUnit.HOURS.toSeconds(hr);
        long min = TimeUnit.SECONDS.toMinutes(eTime);
        eTime -= TimeUnit.MINUTES.toSeconds(min);
        return String.format("%d days, %02d:%02d:%02d", days, hr, min, eTime);
    }

    private static String formatarUnidades(long valor, long prefixo, String unidade) {
        return valor % prefixo == 0L ? String.format("%d %s", valor / prefixo, unidade) : String.format("%.1f %s", (double)valor / (double)prefixo, unidade);
    }

    private static Double formatarUnidades(long valor, long prefixo) {
        return valor % prefixo == 0L ? (double)valor / (double)prefixo : (double)valor / (double)prefixo;
    }
}
