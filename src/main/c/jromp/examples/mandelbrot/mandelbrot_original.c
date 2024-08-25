#include "mandelbrot.h"

int explode(double x, double y, int count_max);
int ppm_write(char *output_filename, int x_size, int y_size, int *r, int *g, int *b);
int ppm_write_data(FILE *file_out, int x_size, int y_size, int *r, int *g, int *b);
int ppm_write_header(FILE *file_out, int x_size, int y_size, int rgb_max);
void timestamp();

int main() {
    int *r;
    int *g;
    int *b;
    int c;
    int c_max;
    int *count;
    const int count_max = COUNT_MAX;
    char *filename = "mandelbrot_c_original.ppm";
    int i;
    int j;
    const int m = IMAGE_SIZE;
    const int n = IMAGE_SIZE;
    double x;
    const double x_max = 1.25;
    const double x_min = -2.25;
    double y;
    const double y_max = 1.75;
    const double y_min = -1.75;
    struct timeval tv1, tv2;
    struct timezone tz;

    timestamp();

    printf("\n");
    printf("MANDELBROT\n");
    printf("  C version\n");
    printf("\n");
    printf("  Create an ASCII PPM image of the Mandelbrot set.\n");
    printf("\n");
    printf("  For each point C = X + i*Y\n");
    printf("  with X range [%f,%f]\n", x_min, x_max);
    printf("  and  Y range [%f,%f]\n", y_min, y_max);
    printf("  carry out %d iterations of the map\n", count_max);
    printf("  Z(n+1) = Z(n)^2 + C.\n");
    printf("  If the iterates stay bounded (norm less than 2)\n");
    printf("  then C is taken to be a member of the set.\n");
    printf("\n");
    printf("  An ASCII PPM image of the set is created using\n");
    printf("    N = %d pixels in the X direction and\n", n);
    printf("    N = %d pixels in the Y direction.\n", n);

    /* Carry out the iteration for each pixel, determining COUNT. */
    count = (int *) malloc(m * n * sizeof(int));
    gettimeofday(&tv1, &tz);

    for (i = 0; i < m; i++) {
        x = ((double) i * x_max
             + (double) (m - i - 1) * x_min)
            / (double) (m - 1);

        for (j = 0; j < n; j++) {
            y = ((double) j * y_max
                 + (double) (n - j - 1) * y_min)
                / (double) (n - 1);

            count[i + j * m] = explode(x, y, count_max);
        }
    }

    /* Set CMAX to the maximum count. */
    c_max = 0;
    for (i = 0; i < m; i++) {
        for (j = 0; j < n; j++) {
            if (c_max < count[i + j * m]) {
                c_max = count[i + j * m];
            }
        }
    }

    gettimeofday(&tv2, &tz);
    const double execution_seconds = tv2.tv_sec - tv1.tv_sec + (tv2.tv_usec - tv1.tv_usec) * 1e-6;
    printf("Wall clock time = %12.4g sec\n", execution_seconds);

    /* Set the image data. */
    r = (int *) malloc(m * n * sizeof(int));
    g = (int *) malloc(m * n * sizeof(int));
    b = (int *) malloc(m * n * sizeof(int));

    for (i = 0; i < m; i++) {
        for (j = 0; j < n; j++) {
            if (count[i + j * m] % 2 == 1) {
                r[i + j * m] = 255;
                g[i + j * m] = 255;
                b[i + j * m] = 255;
            } else {
                c = (int) (255.0 * sqrt(sqrt(sqrt((double) count[i + j * m] / (double) c_max))));
                r[i + j * m] = 3 * c / 5;
                g[i + j * m] = 3 * c / 5;
                b[i + j * m] = c;
            }
        }
    }

    /* Write an image file. */
    ppm_write(filename, m, n, r, g, b);

    printf("\n");
    printf("  ASCII PPM image data stored in \"%s\".\n", filename);

    /* Free memory. */
    free(count);
    free(r);
    free(g);
    free(b);

    /* Terminate. */
    printf("\n");
    printf("MANDELBROT\n");
    printf("  Normal end of execution.\n");
    printf("\n");
    timestamp();

    return 0;
}

int explode(const double x, const double y, const int count_max) {
    int k;
    int explosion_step;
    double x1, x2, y1, y2;

    explosion_step = 0;
    x1 = x;
    y1 = y;

    for (k = 1; k <= count_max; k++) {
        x2 = x1 * x1 - y1 * y1 + x;
        y2 = 2.0 * x1 * y1 + y;

        if (x2 < -2.0 || 2.0 < x2 || y2 < -2.0 || 2.0 < y2) {
            explosion_step = k;
            break;
        }

        x1 = x2;
        y1 = y2;
    }

    return explosion_step;
}

int ppm_write(char *output_filename, const int x_size, const int y_size, int *r, int *g, int *b) {
    FILE *file_out;
    int *r_index;
    int *g_index;
    int *b_index;
    int rgb_max;
    int error;
    int i;
    int j;

    /* Open the output file. */
    file_out = fopen(output_filename, "wt");

    if (!file_out) {
        printf("\n");
        printf("PPM_WRITE - Fatal error!\n");
        printf("  Cannot open the output file \"%s\".\n", output_filename);
        return 1;
    }

    /* Compute the maximum. */
    rgb_max = 0;
    r_index = r;
    g_index = g;
    b_index = b;

    for (j = 0; j < y_size; j++) {
        for (i = 0; i < x_size; i++) {
            if (rgb_max < *r_index) {
                rgb_max = *r_index;
            }
            r_index++;

            if (rgb_max < *g_index) {
                rgb_max = *g_index;
            }
            g_index++;

            if (rgb_max < *b_index) {
                rgb_max = *b_index;
            }
            b_index++;
        }
    }

    /* Write the header. */
    error = ppm_write_header(file_out, x_size, y_size, rgb_max);

    if (error) {
        printf("\n");
        printf("PPM_WRITE - Fatal error!\n");
        printf("  PPM_WRITE_HEADER failed.\n");
        return 1;
    }

    /* Write the data. */
    error = ppm_write_data(file_out, x_size, y_size, r, g, b);

    if (error) {
        printf("\n");
        printf("PPM_WRITE - Fatal error!\n");
        printf("  PPM_WRITE_DATA failed.\n");
        return 1;
    }

    /* Close the file. */
    fclose(file_out);
    return 0;
}

int ppm_write_data(FILE *file_out, const int x_size, const int y_size, int *r, int *g, int *b) {
    int *r_index;
    int *g_index;
    int *b_index;
    int rgb_num;
    int i;
    int j;

    r_index = r;
    g_index = g;
    b_index = b;
    rgb_num = 0;

    for (j = 0; j < y_size; j++) {
        for (i = 0; i < x_size; i++) {
            fprintf(file_out, "%d %d %d", *r_index, *g_index, *b_index);

            rgb_num += 3;
            r_index++;
            g_index++;
            b_index++;

            if (rgb_num % 12 == 0 || i == x_size - 1 || rgb_num == 3 * x_size * y_size) {
                fprintf(file_out, "\n");
            } else {
                fprintf(file_out, " ");
            }
        }
    }

    return 0;
}

int ppm_write_header(FILE *file_out, const int x_size, const int y_size, const int rgb_max) {
    fprintf(file_out, "P3\n");
    fprintf(file_out, "%d %d\n", x_size, y_size);
    fprintf(file_out, "%d\n", rgb_max);

    return 0;
}

void timestamp() {
    static char time_buffer[TIME_SIZE];
    const struct tm *tm;
    time_t now;

    now = time(NULL);
    tm = localtime(&now);

    strftime(time_buffer, TIME_SIZE, "%d %B %Y %I:%M:%S %p", tm);

    printf("%s\n", time_buffer);
}
