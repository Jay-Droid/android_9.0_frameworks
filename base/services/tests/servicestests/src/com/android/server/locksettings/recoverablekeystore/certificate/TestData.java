/*
 * Copyright (C) 2017 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.android.server.locksettings.recoverablekeystore.certificate;

import android.content.Context;
import android.support.test.InstrumentationRegistry;

import com.google.common.io.ByteStreams;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.util.Base64;
import java.util.Date;

/** Constants used by the tests in the folder. */
public final class TestData {

    private static final String TEST_FILE_FOLDER_NAME = "KeyStoreRecoveryControllerTest";

    private TestData() {}

    // Some test data that is generated by using OpenSSL command line tools.
    private static final String ROOT_CA_TRUSTED_BASE64 = ""
            + "MIIJJzCCBQ6gAwIBAgIJAM7fBGeQ1wBkMA0GCSqGSIb3DQEBDQUAMCAxHjAcBgNV"
            + "BAMMFUdvb2dsZSBDcnlwdEF1dGhWYXVsdDAeFw0xODAxMTEwNjQ5MzNaFw0zODAx"
            + "MDYwNjQ5MzNaMCAxHjAcBgNVBAMMFUdvb2dsZSBDcnlwdEF1dGhWYXVsdDCCBCIw"
            + "DQYJKoZIhvcNAQEBBQADggQPADCCBAoCggQBCcFv05M1seLPYIW7oFivh7u5otCt"
            + "Mm7ryq0UjpbTQPcQxJQbYzcUQF7CbPYTdWDid4EuO8Zec03ownsduFhKud6H3yIQ"
            + "4ueGiqCBoG1D6+N8fF9R8awTmAsbNg63VInx6IwcBZnjFlsIIftOFxqIJBYhiKhN"
            + "JhPtF1i9dn+N5HjEKmkJO3pXhYhRXMp7OwL/epxzhBXFYT7aDg9pnaM6C+4hmhQ/"
            + "0q2oyzYtAqFmrEenbtI6G47SzMc+shNTuYLtq21j/Z3uA3RwB9Szfu99F66tlgTX"
            + "v7K7YS573hN3TQY/+nkLfFy/oF2LQRYvKHF+Nv0BHzQLzqDEYBaILcMf3i2Ce/b7"
            + "wZjitLqFAI1swqGzgH/QpB3OrX51M/B7UCF2nB7Pa8knu4kBDGkz2Q41jAL0W/qt"
            + "j43VwJDW0Y98OuqQiCqJrTrGdv7b/phnVVBvFrtIjYMfyK34jy5VLXctV5CSkWj5"
            + "3ul3mvGFHJD+6nneDR4PUkmYN0khT4t/RqnQlwYE0a6Erq1+Rof6/DoWSzeBLBYV"
            + "JaHhRy9mrudR/VcQynLKty6Zst4Lyh6aPMHcpTwGZbG+4mXnWeTaLEnGvivldksT"
            + "XOxipcO/fXJfDss4b0glGzP3GD0+H5EZB9coYzNT47QZd9drxHdrLxtPoi+MeqkG"
            + "gCdyFyBZO8G2k/JuyziT6hy+50VXJnl6Ujxj7MVUYAsISHsHgqETDsukQbbKvTKg"
            + "3gxPVNN/vKWwyh7KLcFIaOEoPOgStkmVsqrXm7YLE6Bvzm8nu4rwJeAF9Yseg9BE"
            + "Y86TRRmAI7fW4eDEPnxgCUUvcYSAh5mcayIyIr0KTuXkevwYbVRHMVmy9DaqzsP8"
            + "YFXIqFvDXRCFSy/gMkoNb9ZoqdkmjZ+VBsjAKI+u/Haf6pgdpGZbVGKEFmaVHCkr"
            + "tPp/gy4kE4qmd/SIaccG8o6Eb9X9fbqTTDZv34kcGgxOvBJVIaNHprTjgvYEnRaD"
            + "KTlmZoCUmBlHzvbf68YWBmIz0K8vYPdx9r98LiUgpbTHtKZIYrJnbgPnbC9icP24"
            + "2ksB4yaTx1QWc14vTNv1lUtv4zJEmaaoynNlETJFf/Tz0QKJxtT+l/BIAz8kEJMA"
            + "cKsfoTx9OTtfuL85pXbCgxbKKmKn6RzxUCzSzgMboC0z6W8Zxy2gLIhqMm8AXAF7"
            + "salwrRirV4lWsM9MOhVEgfjcv/qmQSYr1ARrwwegHRqxPA3qh11kfq5YSFU7W7+f"
            + "JrWH6VuLZ0B1fj2+lsoMNekFA1ULD8DK7aAFIh9Y1y4Jt//xMuOPcD5PWNGFmUk7"
            + "oPewiIUMLjXSWcgrQVYbZEDW/vooMJoo47Vg1fQPehejbONE1nBIaeRVhJcCAwEA"
            + "AaNjMGEwHQYDVR0OBBYEFNd7oYeSi7hSGimRpTZaHLQy6+zRMB8GA1UdIwQYMBaA"
            + "FNd7oYeSi7hSGimRpTZaHLQy6+zRMA8GA1UdEwEB/wQFMAMBAf8wDgYDVR0PAQH/"
            + "BAQDAgGGMA0GCSqGSIb3DQEBDQUAA4IEAgABny011veuPplTcNeyLeKhWRn/y9VM"
            + "QEyhaLlsC1Di35WN8owjj4+gugLlDdtEc/pvzgk+ZkAuplQkrsU907dvwkDPb4rW"
            + "ZB5hjbr9yVyEHK1bHh7RSUkJrB3NRwqH1lyWz/LfaVfbV4CtaaERhThzZCp/MweO"
            + "Tivg2XpSluy5s8lEEbMC/dGuIsBMitX3XLlbYa2ND3aHZLo6X9yQMFfTCjgwYG2n"
            + "eDYupnvcLINlrlJYcrYSrIvoQn9XfsnjU3AXz+jc6xLrO3EtXDhi9e+QTfcnvRsg"
            + "l/Hj9SZr1w1L1PPJo+KjsRavVvzaHXlBAvvUtEojJrkR3j+b5zvQB6dgVrM0zUhM"
            + "Q9zRp5R/xqHeZ/0TTQe9kEa8QuRzuRIkK5Wbh76Eix3S+2uTsbj462nk4E0oPR8p"
            + "iYopS4ZEFEfrKW14HOph9ZscI4l/HfDmTNfgpyFl62UrvzVBnoz+sbhTgbPHPcCX"
            + "OUrhmpz9I5oBkyEAZYunSvzY/9SXUsz6psXHJmVzLQcne/YQTtpWzV/wGD7cjjDl"
            + "bfzsmGCfZ8jqPBoicl5IUVdyZsJgufEZHXxKZQ7wL7R6jKrj/GtCDey1Wr2QT8VX"
            + "5JTk9cJQFjgjDWaAyCBpGEaQvYJcaOxk2D+Wap5ax8nUfW/99vVFA0EJKsSVVzw7"
            + "daRty0UpfZsx2Sfzpg0mymmgB8+NY6t68dL5C/xxAv5mEQ8wGJmP45iQpo5T6LVV"
            + "MrktLf5eIzxlALQIW/AgpSH9JKCqpItdxfisAIIs9e8XHbVJJA0Jde7rtAj+TUY0"
            + "h00xSqyfSSbpcDJ9lIoSZOJvFQdWOxB8c3vZZGGhMuRFm06sUHvcHjo8KwnbqyOx"
            + "DGjeqt6YWty6WcNin0WciR33vGHIzwVNxNnmuY308bNsMvY9jsmd37hdmmwnmQge"
            + "7AIa7TMPjaKm0vV/1ztFSODWCI2K7klmL2MtOJMGfqUeOfjPANbS3lMJBAH9qxLM"
            + "7Kng+nfqVtt+NG9MxcTbP80FkBa/6JxGgjjsiwDmhr2MTCYOK/eD+WZikMOieyvH"
            + "m2vgxYCdWrhaGfc3t6oQ2YO+mXI7e6d3F3a90UUYkBIgje9zu0RLxnBBhuoRyGwv"
            + "uQAlqgMDBZIzTO0Vnwew7KRLdzLhWbiikhi81q6Lg62aWjbdF6Ue6AVXch+dqmr+"
            + "9aVt0Y6ETTS77nrQyglyLKIeNx6cEHDjETXlPYGbCAlrdKAdTA4ngnBZnzGQ/8zg"
            + "tP9zvIJVA6cuOAn8GFEsrb7GN20QSDwyJWrYi6f+m64D9rOK4Jz4t+lEfjcfJeM/"
            + "UcNlhmATcMHXWPCoKkOfll4PBc/Wigv1xYw70RZ4pai07LzJxNHYhvpE3Q==";
    private static final String ROOT_CA_DIFFERENT_KEY_BASE64 = ""
            + "MIIFJjCCAw6gAwIBAgIJANpazyIWdcb/MA0GCSqGSIb3DQEBCwUAMCAxHjAcBgNV"
            + "BAMMFUdvb2dsZSBDcnlwdEF1dGhWYXVsdDAeFw0xODAxMjEwMzI0MjVaFw0zODAx"
            + "MTYwMzI0MjVaMCAxHjAcBgNVBAMMFUdvb2dsZSBDcnlwdEF1dGhWYXVsdDCCAiIw"
            + "DQYJKoZIhvcNAQEBBQADggIPADCCAgoCggIBAKkAUqkRxdVy9UpI9BjQnylGVPRW"
            + "aQsCwT2iWJ7fuCnQQon1U9nOyw2R5GYKcA8Zy+4Co++6nzRblYYXJG3Fzsj+kxei"
            + "pZGmU11djRJDOhHPPe5jSW37Y0czWaj8jx4xvMim18dGYR7fg6SsOOYXA2y5tlvZ"
            + "xLjvw5qpL62J5bVoAAxjng/Oc2Osu+vpv6M50pUZr0OEiFi59WlwrCCZpf1/80bT"
            + "j2ebCKKAtTYa6+Q+oMGGxb3imSRpmQPtFcvhUPmAaocUjYM/FeIGNRv14oED/aXz"
            + "khuPb+QNkXgk9yiokE10IeAk6oNUNDyuiMNIFy67lUrwc45lv9y0s/8fHj9pvKse"
            + "n3+UOKAuV9atUXLdFKQwnQPt4SOmHPkXoj+5tv32RSvVeYhb0ZOpQPkRhxv4wgs7"
            + "NldNbKhzVDM9K4M5Q2TrPK1yJKrc5/z0bDzmPOcH4AAXPvSt5PZOs0NlXUJ99BcA"
            + "KE1sWArUhipz5mx0hxPTNEM9/8bMb//HkbZtx2log1/fc207W/AFd2FICOpRY+Sb"
            + "CJs9WjstpisppotONvgXxZbZiypGKxpeZOb4s6y3iXtZ0FWXUZrc65b8S06fDVCa"
            + "ZomNFDWhspHFKyueBgU6cR9K97cDo85Juy6RhnouXxi+XpXPdGFwPqVm1glFYos8"
            + "5+Scbwwx69RihN1nAgMBAAGjYzBhMB0GA1UdDgQWBBRoosUAVtfHHeMl8/5x1m2m"
            + "arEOoTAfBgNVHSMEGDAWgBRoosUAVtfHHeMl8/5x1m2marEOoTAPBgNVHRMBAf8E"
            + "BTADAQH/MA4GA1UdDwEB/wQEAwIBhjANBgkqhkiG9w0BAQsFAAOCAgEAYbLVQlsi"
            + "KdzCPsaybSrUtMrmkad8Gy+/QR3J16adxy/2WpxsZ1Su0YA4tFzOSWqt74C0mnUi"
            + "i+3prW7nyEOwezs+NH0SreF4B7tO1FSj157LoHxR2WmCCwul8FfsMi0x6MvEf40v"
            + "bMeLrRAA4ysRITua1INb05fzJpczoaW/Q1lBPXConvolnIMvYsLbCZX4/OhQQCLa"
            + "mJx8mGrt1wcNp7Kvh+3JfuOXw+WGeCuB5sSWBsOUvhfN+8sgyk1Dtq5c7rVKKtqz"
            + "gqHfCNZ+lYa6Vkii7plIcWYJXXa7DMmozX07mrDqdJZEocv6XCoZAKDlJorB8pQT"
            + "im47RF60X/FCHSCK2cHbG5M+kF84xwj5fTLztLM1+RlJSJiGk6jhxJRQ+hl0Vkhp"
            + "+u7UbUDwkUF/CJB3d1Gtfm+QtzFVKe27ClU5YFSKCXRV/K4KnkZqpyG8Py+PUtUf"
            + "WRahp4hkWFkIoLeTnJwgAFRvp/KCtSW0/trI/vfInzqBk/qWIVhxYB8Qv9DXtKBX"
            + "3AZ36HM2dxmjef/rpYRphuEN0ZwYdynsGy9dF0SihbR8curSg67sbtYfyw0xURhU"
            + "Nk99YMy7T0EUYnaki/PIPK/gnJjZTX1FLCyHUR38fDJIWkGB4xr8pSrwmRoPSvNF"
            + "dFr3YlFWwHWd1gXlwJtzeMSuVgoKVtZGmmk=";
    private static final String ROOT_CA_DIFFERENT_COMMON_NAME_BASE64 = ""
            + "MIIJFzCCBP6gAwIBAgIJAMobGgw5LXwqMA0GCSqGSIb3DQEBCwUAMBgxFjAUBgNV"
            + "BAMMDVdyb25nIFJvb3QgQ0EwHhcNMTgwMTE2MTgzNDA5WhcNMzgwMTExMTgzNDA5"
            + "WjAYMRYwFAYDVQQDDA1Xcm9uZyBSb290IENBMIIEIjANBgkqhkiG9w0BAQEFAAOC"
            + "BA8AMIIECgKCBAEJwW/TkzWx4s9ghbugWK+Hu7mi0K0ybuvKrRSOltNA9xDElBtj"
            + "NxRAXsJs9hN1YOJ3gS47xl5zTejCex24WEq53offIhDi54aKoIGgbUPr43x8X1Hx"
            + "rBOYCxs2DrdUifHojBwFmeMWWwgh+04XGogkFiGIqE0mE+0XWL12f43keMQqaQk7"
            + "eleFiFFcyns7Av96nHOEFcVhPtoOD2mdozoL7iGaFD/SrajLNi0CoWasR6du0job"
            + "jtLMxz6yE1O5gu2rbWP9ne4DdHAH1LN+730Xrq2WBNe/srthLnveE3dNBj/6eQt8"
            + "XL+gXYtBFi8ocX42/QEfNAvOoMRgFogtwx/eLYJ79vvBmOK0uoUAjWzCobOAf9Ck"
            + "Hc6tfnUz8HtQIXacHs9rySe7iQEMaTPZDjWMAvRb+q2PjdXAkNbRj3w66pCIKomt"
            + "OsZ2/tv+mGdVUG8Wu0iNgx/IrfiPLlUtdy1XkJKRaPne6Xea8YUckP7qed4NHg9S"
            + "SZg3SSFPi39GqdCXBgTRroSurX5Gh/r8OhZLN4EsFhUloeFHL2au51H9VxDKcsq3"
            + "Lpmy3gvKHpo8wdylPAZlsb7iZedZ5NosSca+K+V2SxNc7GKlw799cl8OyzhvSCUb"
            + "M/cYPT4fkRkH1yhjM1PjtBl312vEd2svG0+iL4x6qQaAJ3IXIFk7wbaT8m7LOJPq"
            + "HL7nRVcmeXpSPGPsxVRgCwhIeweCoRMOy6RBtsq9MqDeDE9U03+8pbDKHsotwUho"
            + "4Sg86BK2SZWyqtebtgsToG/Obye7ivAl4AX1ix6D0ERjzpNFGYAjt9bh4MQ+fGAJ"
            + "RS9xhICHmZxrIjIivQpO5eR6/BhtVEcxWbL0NqrOw/xgVcioW8NdEIVLL+AySg1v"
            + "1mip2SaNn5UGyMAoj678dp/qmB2kZltUYoQWZpUcKSu0+n+DLiQTiqZ39Ihpxwby"
            + "joRv1f19upNMNm/fiRwaDE68ElUho0emtOOC9gSdFoMpOWZmgJSYGUfO9t/rxhYG"
            + "YjPQry9g93H2v3wuJSCltMe0pkhismduA+dsL2Jw/bjaSwHjJpPHVBZzXi9M2/WV"
            + "S2/jMkSZpqjKc2URMkV/9PPRAonG1P6X8EgDPyQQkwBwqx+hPH05O1+4vzmldsKD"
            + "FsoqYqfpHPFQLNLOAxugLTPpbxnHLaAsiGoybwBcAXuxqXCtGKtXiVawz0w6FUSB"
            + "+Ny/+qZBJivUBGvDB6AdGrE8DeqHXWR+rlhIVTtbv58mtYfpW4tnQHV+Pb6Wygw1"
            + "6QUDVQsPwMrtoAUiH1jXLgm3//Ey449wPk9Y0YWZSTug97CIhQwuNdJZyCtBVhtk"
            + "QNb++igwmijjtWDV9A96F6Ns40TWcEhp5FWElwIDAQABo2MwYTAdBgNVHQ4EFgQU"
            + "13uhh5KLuFIaKZGlNloctDLr7NEwHwYDVR0jBBgwFoAU13uhh5KLuFIaKZGlNloc"
            + "tDLr7NEwDwYDVR0TAQH/BAUwAwEB/zAOBgNVHQ8BAf8EBAMCAYYwDQYJKoZIhvcN"
            + "AQELBQADggQCAAWZS0RIH4FdQhlUkdJW06cUYYIJ7p8rRI+6XgMkfj7vupjVnfDN"
            + "3Z3s0yf+dGaGGJbv68W952j33z94FuubsacCixNSaS4bIwLXvcL9kQtI9Qf09Pd6"
            + "GB102Q6nz/daLVF/5urQPbp4UFkyhcP/d2mg9CmxwBL3Djrf/dKf4EoiV5xmQpUB"
            + "a6BNBMntnK1VZ3y8YxDF3XnhbjUZAOTLLYe4+isqvXPb9osdmdaZQU3iHQQwGYJN"
            + "6rTYvHmsdfU5eLYLdWxOOm/5Sz5rWXxa1XqSfQgOIaFYQ1w69Z+3BfNbaBnYck3l"
            + "xtxGHromigt+2iimXFFML7EHiSznVhHl3SOX0RBLeUvP8oNwwSsaHXuXbceYWvb+"
            + "ic7FyTN4f3+wRGjN01U3be93dj+qZlvTmCzpOrJeUcym3F94d0tWQvk3kkBp/Egi"
            + "Dd85vYWCdEeCfODW6sReVdj/IuT5xv1T8kKaNaEjJjAjeX6xjPskw3I2LuPeEyz+"
            + "26LiOs1hPRHC4CL3JS6LNmRmIYKuy7K0DHwxS6wplDYXXH+a0VuLvQrbsw5zTh3f"
            + "Xwq0CLGwPPRyMnFk13+PYBa0bmJ1dNu5hUc9biziCJlvcg0c+FzzUYG4poN0R73R"
            + "XPyFHmpULAHit05dw3QaPZqX1GCeiVxrCl6N6G4/9PsVOvi/WEUasHDkk+R4/r9b"
            + "RwvQw0PVdDvGndouRcSzHvPEdW9y2TxSDhltvtC2xvp3mGaT0j2+cCRDINLFB6rK"
            + "v18oLzpzu3HaZ2ptmm4OpeRnCTLa4qheV1rlSWi3mvPoh77glgHEyTHvhiFrlq+6"
            + "f6oMpkcbp4KtOT/npvB3yY4RZWn+J1cDcOW34ssSN2PDVSx1IsianxtSCXKReYrv"
            + "kjS4sTQWw0LYG6146g2rz1OKzuT+6YSIPlpw/4/DK0Sz/Q1AY0cuOHhgMSW0cSaE"
            + "6PQ275hFjJ4zYYEYNOp56nhsvgbLAu1V5rwQpwi2RNo4teFzP/AKyZzNbApfl8Q5"
            + "PKHHE8+Uk3/oLZ8h12JzceKL5ivXU4i8r9sw+o7b/UReVsbrFDXTuO3sRFyA5kI0"
            + "aBpYrAyb8xVubbi7gCWhDfJsSKc7CR2N+EUtBrT08TD0AtuxpRLr6Sz2RrTabZNV"
            + "lQlgWmKalWT/i7gyA/MNxbBK0hyr7Pvl5f7Ud2+muKOfhEXiCzp8yzXU9I8cTz2p"
            + "K85LYJfRNIO2kPrNLdomPL+J2S298GvX2j08CZR8qBXLOs6XJOvZ1KC8BrJ9M7kG"
            + "2MHGXeL+9/11khM09dbC+Q0NUKTKOpSU7M7RaON1Dp4RyzdIcdwZ/dFvvxcYbtOP"
            + "6OAzRvlk6CZWG3obkt3yaB1NhBEw8hiYvX9F";
    private static final String INTERMEDIATE_CA_1_BASE64 = ""
            + "MIIHMDCCAxegAwIBAgICEAEwDQYJKoZIhvcNAQELBQAwIDEeMBwGA1UEAwwVR29v"
            + "Z2xlIENyeXB0QXV0aFZhdWx0MB4XDTE4MDExMjAwMjM1N1oXDTI4MDExMDAwMjM1"
            + "N1owLTErMCkGA1UEAwwiR29vZ2xlIENyeXB0QXV0aFZhdWx0IEludGVybWVkaWF0"
            + "ZTCCAiIwDQYJKoZIhvcNAQEBBQADggIPADCCAgoCggIBALlhoKYuwxaatY9ERDFp"
            + "iEygSnjy0xzaiF4uCyiTfAuboSi5QwGon3ohf0ufJF02L9lnTMoeBAg+88m8AMgW"
            + "KFcEupabqlZfA3F/50mMCmnJvBSLXJ+chUdcAVpwcZAsq6ko22ARBxao1wu2qxNe"
            + "D8eXiiK8DRpTtKy3wOldZJ222v35v9JGOTjORZRrcv7Z8f6I5/cSsTS+WoVk/aBt"
            + "QqyFkcdw1zqulnlFE2rxNAVgyLlW71WJikYTDtUDeo79LvkDGjVsLo2MfpNxuK+5"
            + "MuMqzyN5LmzXJmNCEW1O5IIIUAPhgy5s08G+3G644wCEWsAnv2FBWLBn/HmJu6Uq"
            + "nSM2AaJN56V0tJG/yL2VgTnPJrJypNTKZW3OTCLCaYcTEbKfarxLwVWxvIWSIgkn"
            + "0q57GYhf7O+x9vvcOUmZwVxZECorIiK4n5AWG/KD3dWI3UGGGpYsDazRngA/bQPu"
            + "DSzBP9FBVcQt3/DMBG1s6f2Eko5f6aTFcVW9iV7aWLeIq+pQYlbmG42decj+aHLQ"
            + "COp5KV+Q77y4kFhZQFAQ1mN4crnhuEc1K5SmjAK24zIqWbwM3ly0KSQFc9jAmONg"
            + "0xu7kAObP3PZk85En12yLLscNmHCWYfOOEvTHf4KX7tjBl4HHp/ur+2Qwgpt9MFB"
            + "MGqR2cni5OV6gZcRdHaEerjrAgMBAAGjZjBkMB0GA1UdDgQWBBRE9RxHT7U3EP1v"
            + "djRzNYMrU7EseDAfBgNVHSMEGDAWgBTXe6GHkou4UhopkaU2Why0Muvs0TASBgNV"
            + "HRMBAf8ECDAGAQH/AgEBMA4GA1UdDwEB/wQEAwIBhjANBgkqhkiG9w0BAQsFAAOC"
            + "BAIAAfa7FKkfh4t+G8Sv4n3gUcuEAtmpCsTtHJU67r5eSvR24fDX7sqqSIib9sQ8"
            + "adA1FtnE3wnC4bEKnIwQLerWTN4i4V9oEKRIT5o5Rr61LyO9R+Prpo6uJVoPujFH"
            + "GZPdIj/Qs4Xax4a11QD+70+ZmmdL6yfigVDWA4yduFdMg6heHRf3EFCbVBv5qbBS"
            + "n8+eFKRnBZ/kQdFlYt+G+92Qqyn2uAcER6kZjIfPdnZh44SazLP37W8AkDX30Pmk"
            + "V0PHVGCDrap44q3maI1m8NAE1jGwwmRzJL953x5XgbVGt0K/3cNoWtKLenwX/G3I"
            + "akrgvOY5Zl0v3FRDZwGFt9UIBfZDDOGRMXIgIGs/1cvkwWpOT6dyReqDXempiQ1q"
            + "Yy6J5VsK5WK6gEelUyoACbzgby25V6a79Q1MI7dXmFQfCcX0nAD/AZmM1HkeYgrC"
            + "uq6fWoPOVMKML2mN90rCzkWxGaLcl5dPfad0O1LrcP48SRE5MXMWyxZZBon+wDIk"
            + "ascyM/r4fmk4kq64YKdm2wxCDMNArAIcyBkwOaWWfabtSagxJ3qtMtxK0qBUsbLC"
            + "yMyYpgU1h9c8rEdc4JgeE2LXJzxTKDc3SBOqbuNMlKWjYA+X+SUvVYALrQKAC+5v"
            + "wdUhLYdAPAksqk/ZoiBjkW35FfvqQMJBY29VnDT1h7/Nxk5gu+goTA9oFIYNrNte"
            + "+s0my+IUgYhKJBsgh7Mupv+B92GN5b3b440BMHB5QR959Jdq6BAXNUyZLM5fhZQE"
            + "Jj/rxZFXaqq757kgUhwWBz5TDbYF7GkqTyM4k430xwJKY0AYYEHmv1UYNo5X4G3x"
            + "SC2LhWC1b9VAykdkHbLs+IA8klxURmLmRiRj1UryhQjjT8h/FvNyPnbT1AKoElix"
            + "QLnLi8thkJ+tQggO0hISFsIrKNfnn0V6O0VKw9UZsMigsbYG5EbzIXcAyy8Avr9n"
            + "um7gBBZDt7fWso0+pG1UenJ+PybeuW/azQDLRw1Syz8OwU+ABRLq0JyyAtV7VPY5"
            + "C9pkKS+bU8nECxr6dMhAbpLBHlKsyb1qtkOt1p7WagEQZFIIc6svc73+L/ET/lWn"
            + "GBmkVVsCN7Aqyo5aXQWueXP4FUL+6O5+JALqw3qPeQgfnLkh0cUuccNND05QeEiv"
            + "Zswc/23KJXy1XbdVKT3UP0RAF7DxstbRGQbAT3z+n931e3KhtU28OKjsFtoeq2Dj"
            + "6STPEXh4rYFWMM8+DrJetAtBqk/i+vBwRA8f7jqIPPep/vEjPqqMOpdSVcoFQ1df"
            + "JuOZtGfEUjFHnlDr3eGP7KUIEZvhan1zm544dDgPVTXxrY4moi2BhKEY69zRSX6B"
            + "+a0fa5B3pxc8BN0LsHA0stT/Y2o=";
    private static final String INTERMEDIATE_CA_2_BASE64 = ""
            + "MIIESTCCAjGgAwIBAgICEAAwDQYJKoZIhvcNAQELBQAwLTErMCkGA1UEAwwiR29v"
            + "Z2xlIENyeXB0QXV0aFZhdWx0IEludGVybWVkaWF0ZTAeFw0xODAxMTIwMDM4MDNa"
            + "Fw0yMzAxMTEwMDM4MDNaMDoxODA2BgNVBAMML0dvb2dsZSBDcnlwdEF1dGhWYXVs"
            + "dCBJbnRlcm1lZGlhdGUgSW50ZXJtZWRpYXRlMIIBIjANBgkqhkiG9w0BAQEFAAOC"
            + "AQ8AMIIBCgKCAQEA0v3bN3MwKifDAkF1524XzuaxYtk1sQKUlAlNngh+Qv4RjCkX"
            + "TECi7po8LeNsY+hWxmW3XZ22RBphe/yP4YcOdbqlIjZYNx3b75hCSJCadOkdW+Z9"
            + "f6+tKsHgeUja6r9r2TThzileImAvjXShe7GZYW8csPv6HaEVRXQlu8fGAZf8skmJ"
            + "EMfJx84//WeULdVz94beDhi9YAf4gLfmOayQcdWhDcMYI39knJcRny1ffRGgb1Hf"
            + "lE+3/a3aGFeODaxfkPaGRxEhzhZ/JDBiNgUAH/u7C5nxqa2WOu5e0wq3S0TndIOE"
            + "hmnwCE2GvxADFQst+rSsOn4EHit70hv4CfrMRQIDAQABo2YwZDAdBgNVHQ4EFgQU"
            + "0dKv4xTaEmdwHyox3tY8H8XVDGIwHwYDVR0jBBgwFoAURPUcR0+1NxD9b3Y0czWD"
            + "K1OxLHgwEgYDVR0TAQH/BAgwBgEB/wIBADAOBgNVHQ8BAf8EBAMCAYYwDQYJKoZI"
            + "hvcNAQELBQADggIBAJaArqLlJ2SLQ8JRwanD6LPlqQxucQ+x/LztTQpzPrsFfJds"
            + "E/sDZr2rXhDpz/bifIdj/DCbQ6/3w+t5JdFjT8GjXLgz1kCa/4W409FgSTgy1ENn"
            + "AMUU6pFIbOq/Qy/71uOTrrFWYTN5Vk+RBGxx5iDfHjDYraudi4VlcNkal4OyM98n"
            + "N3qp9cZD0RtWxMhvq6ahgmf9cTbEw6+l8yf/bogGLBYXXYeOoO5Q134AxrrgfthE"
            + "tvyKwJkT/l3OFKRcaHrebs+V1z5gPs7zWOyO5n2Z1SAmcOGfTfKMZWwp3Hi3OTr2"
            + "gB3LUYKyQVhC70dka3X+IbnFg5YfzJtX6YGnHlnI1SufOkEpGQDfcc0UQAWg/lgb"
            + "RkfMFD9tuJomBhyqv1YaxLN8yL4ZTRU0KCvvC5I5+X/zt9kBjnHlBOdYtknZT5jz"
            + "7+mjqWdpmWoAjeV5+CgIzG2k7JAm6rQuE1ZQNF0wAYxPret4NHPJFqfD5gGhdrYw"
            + "pEUxkcwHERA/E1CkpyqUy/Hd3kqHvnEDqzFcxBdUdmOgnbpI2nAZdEpfxmA5+M1n"
            + "UoxQ8ZWAZH+Mdlkw/Hx5hVjGjz8snD4QN25pj/wT+V6AR5OmYb8yfsQb2S8a8yDp"
            + "HzcIHW+dEWpX2boirOsrdI16kNtxYqtG7c5qWBPJy5Zjkvh9qbnfT/RQx10g";
    private static final String LEAF_CERT_1_BASE64 = ""
            + "MIIDCDCB8aADAgECAgYBYOlweDswDQYJKoZIhvcNAQELBQAwLTErMCkGA1UEAwwi"
            + "R29vZ2xlIENyeXB0QXV0aFZhdWx0IEludGVybWVkaWF0ZTAeFw0xODAxMTEwODE1"
            + "NTBaFw0yMDAxMTIwODE1NTBaMCkxJzAlBgNVBAMTHkdvb2dsZSBDcnlwdEF1dGhW"
            + "YXVsdCBJbnN0YW5jZTBZMBMGByqGSM49AgEGCCqGSM49AwEHA0IABLgAERiYHfBu"
            + "tJT+htocB40BtDr2jdxh0EZJlQ8QhpMkZuA/0t/zeSAdkVWw5b16izJ9JVOi/KVl"
            + "4b0hRH54UvowDQYJKoZIhvcNAQELBQADggIBABZALhC9j3hpZ0AgN0tsqAP2Ix21"
            + "tNOcvo/aFJuSFanOM4DZbycZEYAo5rorvuFu7eXETBKDGnI5xreNAoQsaj/dyCHu"
            + "HKIn5P7yCmKvG2sV2TQ5go+0xV2x8BhTrtUWLeHvUbM3fXipa3NrordbA8MgzXwr"
            + "GR1Y1FuMOn5n4kiuHJ2sQTbDdzSQSK5VpH+6rjARlfOCyLUX0u8UKRRH81qhIQWb"
            + "UFMp9q1CVfiLP2O3CdDdpZXCysdflIb62TWnma+I8jqMryyxrMVs9kpfa8zkX9qe"
            + "33Vxp+QaQTqQ07/7KYVw869MeFn+bXeHnjUhqGY6S8M71vrTMG3M5p8Sq9LmV8Y5"
            + "7YB5uqKap2Inf0FOuJS7h7nVVzU/kOFkepaQVHyScwTPuuXNgpQg8XZnN/AWfRwJ"
            + "hf5zE6vXXTHMzQA1mY2eEhxGfpryv7LH8pvfcyTakdBlw8aMJjKdre8xLLGZeVCa"
            + "79plkfYD0rMrxtRHCGyTKGzUcx/B9kYJK5qBgJiDJLKF3XwGbAs/F8CyEPihjvj4"
            + "M2EoeyhmHWKLYsps6+uTksJ+PxZU14M7672K2y8BdulyfkZIhili118XnRykKkMf"
            + "JLQJKMqZx5O0B9bF8yQdcGKEGEwMQt5ENdH8HeiwLm4QS3VzFXYetgUPCM5lPDIp"
            + "BuwwuQxvQDF4pmQd";
    private static final String LEAF_CERT_2_BASE64 = ""
            + "MIICrDCCAZSgAwIBAgICEAAwDQYJKoZIhvcNAQELBQAwOjE4MDYGA1UEAwwvR29v"
            + "Z2xlIENyeXB0QXV0aFZhdWx0IEludGVybWVkaWF0ZSBJbnRlcm1lZGlhdGUwHhcN"
            + "MTgwMTEyMDEwMzA5WhcNMTkwMTEyMDEwMzA5WjArMSkwJwYDVQQDDCBHb29nbGUg"
            + "Q3J5cHRBdXRoVmF1bHQgSW5zdGFuY2UgMjBZMBMGByqGSM49AgEGCCqGSM49AwEH"
            + "A0IABGhmBQyWdjsXKJRbkW4iIrvt6iqhX5t2XGt/vZS9CoOl0fs+EvJXo4kgrnx8"
            + "/8SGxz3pwRkFhY943QYy6a1gv/2jgZUwgZIwCQYDVR0TBAIwADAdBgNVHQ4EFgQU"
            + "xFmLyxUS2JHKURBtewBKRP6kQBgwVgYDVR0jBE8wTYAU0dKv4xTaEmdwHyox3tY8"
            + "H8XVDGKhMaQvMC0xKzApBgNVBAMMIkdvb2dsZSBDcnlwdEF1dGhWYXVsdCBJbnRl"
            + "cm1lZGlhdGWCAhAAMA4GA1UdDwEB/wQEAwIDCDANBgkqhkiG9w0BAQsFAAOCAQEA"
            + "EJWpl7HU6LxukLqhw2tVZr7IRrKIucRk+RKaaiMx1Hx2jsTTskiJRiZas/xoPSqX"
            + "z1K5DVgI486i7HyqnWkGH5xVzCsv+rya5FOSTS3jVtgtoA4HFEqeeAcDowPDqVw3"
            + "yFTA55ukZnzVaPLpDfPqkhzWiuLQ/4fI6YCmOnWB8KtHTMdyGsDSAkpoxpok++NJ"
            + "Lu79BoBLe2ucjN383lTlieLxmrmHjF9ryYSQczcm0v6irMOMxEovw5iT4LHiEhbm"
            + "DfOPW909fe/s+K3TGZ3Q6U77x8g5k9dVovMgA4pFwtREtknFjeK1wXR3/eXGcP3W"
            + "0bMX1yTWYJQFWCG3DFoC5w==";

    /** The cert of the root CA. */
    static final X509Certificate ROOT_CA_TRUSTED = decodeBase64Cert(ROOT_CA_TRUSTED_BASE64);
    /** This root CA cert has a different Common Name than ROOT_CA_TRUSTED. */
    static final X509Certificate ROOT_CA_DIFFERENT_COMMON_NAME =
            decodeBase64Cert(ROOT_CA_DIFFERENT_COMMON_NAME_BASE64);
    /** This root CA cert has the same CN as ROOT_CA_TRUSTED, but a different public key. */
    static final X509Certificate ROOT_CA_DIFFERENT_KEY =
            decodeBase64Cert(ROOT_CA_DIFFERENT_KEY_BASE64);
    /** This intermediate CA cert is signed by the corresponding private key of ROOT_CA_TRUSTED. */
    static final X509Certificate INTERMEDIATE_CA_1 = decodeBase64Cert(INTERMEDIATE_CA_1_BASE64);
    /** This intermediate CA cert is signed by the private key of INTERMEDIATE_CA_1. */
    static final X509Certificate INTERMEDIATE_CA_2 = decodeBase64Cert(INTERMEDIATE_CA_2_BASE64);
    /** This leaf cert is signed by the corresponding private key of INTERMEDIATE_CA_1. */
    static final X509Certificate LEAF_CERT_1 = decodeBase64Cert(LEAF_CERT_1_BASE64);
    /** This leaf cert is signed by the corresponding private key of INTERMEDIATE_CA_2. */
    static final X509Certificate LEAF_CERT_2 = decodeBase64Cert(LEAF_CERT_2_BASE64);

    private static X509Certificate decodeBase64Cert(String str) {
        try {
            byte[] bytes = Base64.getDecoder().decode(str);
            CertificateFactory cf = CertificateFactory.getInstance("X.509");
            return (X509Certificate) cf.generateCertificate(new ByteArrayInputStream(bytes));
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    static final Date DATE_ALL_CERTS_VALID = new Date(1516406400000L); // Jan 20, 2018
    static final Date DATE_LEAF_CERT_2_EXPIRED = new Date(1547254989001L); // Jan 12, 2019
    static final Date DATE_INTERMEDIATE_CA_2_EXPIRED = new Date(1673397483001L); // Jan 11, 2023

    static InputStream openTestFile(String relativePath) throws Exception {
        Context context = InstrumentationRegistry.getContext();
        return context.getResources().getAssets().open(TEST_FILE_FOLDER_NAME + "/" + relativePath);
    }

    static byte[] readTestFile(String relativePath) throws Exception {
        InputStream in = openTestFile(relativePath);
        return ByteStreams.toByteArray(in);
    }
}