DESCRIPTION = "enigma.info used by BoxInfo"
SUMMARY = "enigma.info used by BoxInfo"
PRIORITY = "required"
MAINTAINER = "OpenPLi team"

require conf/license/openpli-gplv2.inc

deltask fetch
deltask unpack
deltask patch
deltask prepare_recipe_sysroot
deltask configure
deltask compile
deltask source_date_epoch

SSTATE_SKIP_CREATION = "1"

inherit linux-kernel-base
KERNEL_VERSION = "${@get_kernelversion_headers('${STAGING_KERNEL_DIR}') or oe.utils.read_file('${PKGDATA_DIR}/kernel-depmod/kernel-abiversion')}"
IMAGE_VERSION = "${DISTRO_VERSION}"

PACKAGE_ARCH = "${MACHINE_ARCH}"
PV = "${IMAGE_VERSION}"
PR[vardepsexclude] = "DATE"

PACKAGES = "${PN}"

inherit python3-dir 

# Hardware Branding

#Display type
DISPLAY_TYPE = "\
${@bb.utils.contains('MACHINE_FEATURES', 'textlcd', 'textlcd' , '', d)}\
${@bb.utils.contains('MACHINE_FEATURES', '7segment', '7segment' , '', d)}\
${@bb.utils.contains('MACHINE_FEATURES', 'bwlcd96', 'bwlcd96' , '', d)}\
${@bb.utils.contains('MACHINE_FEATURES', 'bwlcd128', 'bwlcd128' , '', d)}\
${@bb.utils.contains('MACHINE_FEATURES', 'bwlcd140', 'bwlcd140' , '', d)}\
${@bb.utils.contains('MACHINE_FEATURES', 'bwlcd255', 'bwlcd255' , '', d)}\
${@bb.utils.contains('MACHINE_FEATURES', 'colorlcd', 'colorlcd' , '', d)}\
${@bb.utils.contains('MACHINE_FEATURES', 'colorlcd220', 'colorlcd220' , '', d)}\
${@bb.utils.contains('MACHINE_FEATURES', 'colorlcd240', 'colorlcd240' , '', d)}\
${@bb.utils.contains('MACHINE_FEATURES', 'colorlcd128', 'colorlcd128' , '', d)}\
${@bb.utils.contains('MACHINE_FEATURES', 'colorlcd400', 'colorlcd400' , '', d)}\
${@bb.utils.contains('MACHINE_FEATURES', 'colorlcd390', 'colorlcd390' , '', d)}\
${@bb.utils.contains('MACHINE_FEATURES', 'colorlcd480', 'colorlcd480' , '', d)}\
${@bb.utils.contains('MACHINE_FEATURES', 'colorlcd720', 'colorlcd720' , '', d)}\
${@bb.utils.contains('MACHINE_FEATURES', 'colorlcd800', 'colorlcd800' , '', d)}\
"

# Connectors
HAVE_HDMI = "${@bb.utils.contains('MACHINE_FEATURES', 'HDMI', 'True' , 'False', d)}"
HAVE_YUV = "${@bb.utils.contains('MACHINE_FEATURES', 'YUV', 'True' , 'False', d)}"
HAVE_RCA = "${@bb.utils.contains('MACHINE_FEATURES', 'RCA', 'True' , 'False', d)}"
HAVE_AV_JACK = "${@bb.utils.contains('MACHINE_FEATURES', 'AV_JACK', 'True' , 'False', d)}"
HAVE_SCART = "${@bb.utils.contains('MACHINE_FEATURES', 'SCART', 'True' , 'False', d)}"
HAVE_SCART_YUV = "${@bb.utils.contains('MACHINE_FEATURES', 'SCART-YUV', 'True' , 'False', d)}"
HAVE_NO_SCART_SWITCH = "${@bb.utils.contains('MACHINE_FEATURES', 'NO_SCART_SWITCH', 'True' , 'False', d)}"
HAVE_DVI = "${@bb.utils.contains('MACHINE_FEATURES', 'DVI', 'True' , 'False', d)}"
HAVE_SVIDEO = "${@bb.utils.contains("MACHINE_FEATURES", "SVIDEO", "True", "False", d)}"

#Extra Features
HAVE_HDMI_IN_HD = "${@bb.utils.contains('MACHINE_FEATURES', 'HDMI-IN-HD', 'True' , 'False', d)}"
HAVE_HDMI_IN_FHD = "${@bb.utils.contains('MACHINE_FEATURES', 'HDMI-IN-FHD', 'True' , 'False', d)}"
HAVE_WOL = "${@bb.utils.contains('MACHINE_FEATURES', 'WOL', 'True' , 'False', d)}"
HAVE_WWOL = "${@bb.utils.contains('MACHINE_FEATURES', 'WWOL', 'True' , 'False', d)}"
HAVE_CI = "${@bb.utils.contains('MACHINE_FEATURES', 'ci', 'True' , 'False', d)}"
HAVE_TRANSCODING = "${@bb.utils.contains('MACHINE_FEATURES', 'transcoding', 'True' , 'False', d)}"
HAVE_MULTITRANSCODING = "${@bb.utils.contains('MACHINE_FEATURES', 'multitranscoding', 'True' , 'False', d)}"
HAVE_FHDSKIN = "${@bb.utils.contains('MACHINE_FEATURES', 'skins1080', 'True' , 'False', d)}"
HAVE_SMALLFLASH = "${@bb.utils.contains("MACHINE_FEATURES", "smallflash", "True", "False", d)}"
HAVE_MIDDLEFLASH = "${@bb.utils.contains("MACHINE_FEATURES", "middleflash", "True", "False", d)}"
HAVE_VFDSYMBOL = "${@bb.utils.contains("MACHINE_FEATURES", "vfdsymbol", "True", "False", d)}"
HAVE_KEXECMB = "${@bb.utils.contains("MACHINE_FEATURES", "kexecmb", "True", "False", d)}"

RCTYPE ??= "0"
RCNAME ??= "dmm1"
RCIDNUM ??= "2"
RCHARDWARE ??= "N/A"

STB_PLATFORM ?= "${MACHINE}"
MEDIASERVICE ?= "${@bb.utils.contains("MACHINE_FEATURES", "himedia", "servicehisilicon" , "servicegstreamer", d)}"
BLINDSCAN_BINARY ?= "blindscan"
FORCE ?= "no"
SUPPORT_DBOXLCD ?= "${@bb.utils.contains_any("MACHINE_FEATURES", "textlcd", "True", "False", d)}"
DEVELOPER_NAME ?= "${DISTRO_NAME}"
FRIENDLY_FAMILY ?= "${MACHINE}"
HDMISTANDBY_MODE ?= "${@bb.utils.contains_any("MACHINE_FEATURES", "HDMISTANDBY", "1", "0", d)}"
TIMERWAKEUP_MODE ?= "${@bb.utils.contains_any("MACHINE_FEATURES", "TIMERWAKEUP", "1", "0", d)}"

INFOFILE = "${libdir}/enigma.info"

do_install[nostamp] = "1"

do_install() {
    DRIVERSDATE='N/A'

#   Specific machines
    if [ "${MACHINE}" = "vusolo4k" -o "${MACHINE}" = "vusolo2" -o "${MACHINE}" = "vusolose" -o "${MACHINE}" = "vuduo2" -o "${MACHINE}" = "vuuno4k" -o "${MACHINE}" = "vuuno4kse" -o "${MACHINE}" = "vuultimo4k" -o "${MACHINE}" = "vuzero4k" -o "${MACHINE}" = "vuduo4k" -o "${MACHINE}" = "vuduo4kse" ]; then
        DRIVERSDATE=`grep "SRCDATE = " ${BSP-BASE-VUPLUS}/recipes-bsp/drivers/vuplus-dvb-proxy-${MACHINE}.bb | cut -b 12-19`
    elif [ "${MACHINE}" = "dm8000" ]; then
        DRIVERSDATE="20140604"
    elif [ "${MACHINE}" = "hd2400" ]; then
        DRIVERSDATE=`grep "SRCDATE = " ${BSP-BASE-GFUTURES}/recipes-bsp/drivers/hd-dvb-modules-${MACHINE}.bb | cut -b 12-19`
    elif [ "${MACHINE}" = "bre2ze4k" ]; then
        DRIVERSDATE=`grep "SRCDATE = " ${BSP-BASE-GFUTURES}/recipes-bsp/drivers/hd-dvb-modules-${MACHINE}.bb | cut -b 12-19`
    elif [ "${MACHINE}" = "et1x000" -o "${MACHINE}" = "et7000mini" ]; then
        DRIVERSDATE=`grep "SRCDATE = " ${BSP-BASE-GI}/recipes-bsp/drivers/nextv-dvb-modules-${MACHINE}.bb | cut -b 12-19`
    elif [ "${MACHINE}" = "gbquad4k" -o "${MACHINE}" = "gbue4k" ]; then
        DRIVERSDATE=`grep "SRCDATE = " ${BSP-BASE-GIGABLUE}/recipes-bsp/drivers/gigablue-platform-util-gb7252.bb | cut -b 12-19`
    elif [ "${MACHINE}" = "xpeedc" ]; then
        DRIVERSDATE=`grep "SRCDATE = " ${BSP-BASE-XPEEDC}/recipes-bsp/drivers/nextv-dvb-modules-${MACHINE}.bb | cut -b 12-19`
#   Retail brands
    elif [ "${MACHINE_BRAND}" = "AB-COM" ]; then
        DRIVERSDATE=`grep "SRCDATE = " ${BSP-BASE-ABCOM}/recipes-bsp/drivers/abcom-dvb-modules-${MACHINE}.bb | cut -b 12-19`
    elif [ "${MACHINE_BRAND}" = "AMIKO" ]; then
        DRIVERSDATE=`grep "SRCDATE = " ${BSP-BASE-AMIKO}/recipes-bsp/drivers/amiko-dvb-modules-${MACHINE}.bb | cut -b 12-19`
    elif [ "${MACHINE_BRAND}" = "AXAS" ]; then
        DRIVERSDATE=`grep "SRCDATE = " ${BSP-BASE-AXAS}/recipes-bsp/drivers/axas-dvb-modules-${MACHINE}.bb | cut -b 12-19`
    elif [ "${MACHINE_BRAND}" = "Edision" ]; then
        DRIVERSDATE=`grep "SRCDATE = " ${BSP-BASE-EDISION}/recipes-bsp/drivers/edision-dvb-modules-${MACHINE}.bb | cut -b 12-19`
    elif [ "${MACHINE_BRAND}" = "Formuler" ]; then
        DRIVERSDATE=`grep "SRCDATE = " ${BSP-BASE-FORMULER}/recipes-bsp/drivers/formuler-dvb-modules-${MACHINE}.bb | cut -b 12-19`
    elif [ "${MACHINE_BRAND}" = "Miraclebox" ]; then
        DRIVERSDATE=`grep "SRCDATE = " ${BSP-BASE-MIRACLEBOX}/recipes-bsp/drivers/miraclebox-dvb-modules-${MACHINE}.bb | cut -b 12-19`
    elif [ "${MACHINE_BRAND}" = "Mut@nt" ]; then
        DRIVERSDATE=`grep "SRCDATE = " ${BSP-BASE-GFUTURES}/recipes-bsp/drivers/hd-dvb-modules-${MACHINE}.bb | cut -b 12-19`
    elif [ "${MACHINE_BRAND}" = "Gigablue" ]; then
        DRIVERSDATE=`grep "SRCDATE = " ${BSP-BASE-GIGABLUE}/recipes-bsp/drivers/gigablue-dvb-modules-${MACHINE}.bb | cut -b 12-19`
    elif [ "${MACHINE_BRAND}" = "Maxytec" ]; then
        DRIVERSDATE=`grep "SRCDATE = " ${BSP-BASE-MAXYTEC}/recipes-bsp/drivers/maxytec-dvb-modules-${MACHINE}.bb | cut -b 12-19`
    elif [ "${MACHINE_BRAND}" = "Octagon" ]; then
        DRIVERSDATE=`grep "SRCDATE = " ${BSP-BASE-OCTAGON}/recipes-drivers/octagon-dvb-modules-${MACHINE}.bb | cut -b 12-19`
    elif [ "${MACHINE_BRAND}" = "qviart" ]; then
        DRIVERSDATE=`grep "SRCDATE = " ${BSP-BASE-QVIART}/recipes-drivers/qviart-dvb-modules-${MACHINE}.bb | cut -b 12-19`
    elif [ "${MACHINE_BRAND}" = "SAB" ]; then
        DRIVERSDATE=`grep "SRCDATE = " ${BSP-BASE-SAB}/recipes-bsp/drivers/sab-dvb-modules-${MACHINE}.bb | cut -b 12-19`
    elif [ "${MACHINE_BRAND}" = "SPYCAT" ]; then
        DRIVERSDATE=`grep "SRCDATE = " ${BSP-BASE-SPYCAT}/recipes-bsp/spycat-dvb-modules/spycat-dvb-modules-${MACHINE}.bb | cut -b 12-19`
    elif [ "${MACHINE_BRAND}" = "uclan" ]; then
        DRIVERSDATE=`grep "SRCDATE = " ${BSP-BASE-UCLAN}/recipes-drivers/uclan-dvb-modules-${MACHINE}.bb | cut -b 12-19`
    elif [ "${MACHINE_BRAND}" = "Vimastec" ]; then
        DRIVERSDATE=`grep "SRCDATE = " ${BSP-BASE-GFUTURES}/recipes-bsp/drivers/hd-dvb-modules-${MACHINE}.bb | cut -b 12-19`
    elif [ "${MACHINE_BRAND}" = "VU+" ]; then
        DRIVERSDATE=`grep "SRCDATE = " ${BSP-BASE-VUPLUS}/recipes-bsp/drivers/vuplus-dvb-modules-${MACHINE}.bb | cut -b 12-19`
    elif [ "${MACHINE_BRAND}" = "MaxDigital" ]; then
        DRIVERSDATE=`grep "SRCDATE = " ${BSP-BASE-XP}/recipes-bsp/drivers/xp-dvb-modules-${MACHINE}.bb | cut -b 12-19`
    elif [ "${MACHINE_BRAND}" = "XSARIUS" ]; then
        DRIVERSDATE=`grep "SRCDATE = " ${BSP-BASE-XSARIUS}/recipes-bsp/drivers/xsarius-dvb-modules-${MACHINE}.bb | cut -b 12-19`
    elif [ "${MACHINE_BRAND}" = "XTREND" ]; then
        DRIVERSDATE=`grep "SRCDATE = " ${BSP-BASE-XTREND}/recipes-bsp/drivers/et-dvb-modules-${MACHINE}.bb | cut -b 12-19`
    elif [ "${MACHINE_BRAND}" = "Zgemma" ]; then
        DRIVERSDATE=`grep "SRCDATE = " ${BSP-BASE-ZGEMMA}/recipes-bsp/drivers/zgemma-dvb-modules-${MACHINE}.bb | cut -b 12-19`
    fi

    install -d ${D}${libdir}
    printf "architecture=${DEFAULTTUNE}\n" > ${D}${INFOFILE}
    printf "avjack=${HAVE_AV_JACK}\n" >> ${D}${INFOFILE}
    printf "blindscanbinary=${BLINDSCAN_BINARY}\n" >> ${D}${INFOFILE}
    printf "brand=${BRAND_OEM}\n" >> ${D}${INFOFILE}
    printf "ci=${HAVE_CI}\n" >> ${D}${INFOFILE}
    printf "compiledate='${DATE}'\n" >> ${D}${INFOFILE}
    printf "dboxlcd=${SUPPORT_DBOXLCD}\n" >> ${D}${INFOFILE}
    printf "developername=${DEVELOPER_NAME}\n" >> ${D}${INFOFILE}
    printf "displaybrand=${MACHINE_BRAND}\n" >> ${D}${INFOFILE}
    printf "displaydistro=OpenPLi\n" >> ${D}${INFOFILE}
    printf "displaymodel=${MACHINE_NAME}\n" >> ${D}${INFOFILE}
    printf "displaytype=${DISPLAY_TYPE}\n" >> ${D}${INFOFILE}
    printf "distro=${DISTRO_NAME}\n" >> ${D}${INFOFILE}
    printf "driversdate='${DRIVERSDATE}'\n" >> ${D}${INFOFILE}
    printf "dvi=${HAVE_DVI}\n" >> ${D}${INFOFILE}
    printf "feedsurl=${DISTRO_FEED_URI}\n" >> ${D}${INFOFILE}
    printf "fhdskin=${HAVE_FHDSKIN}\n" >> ${D}${INFOFILE}
    printf "forcemode=${FORCE}\n" >> ${D}${INFOFILE}
    printf "fpu=${TARGET_FPU}\n" >> ${D}${INFOFILE}
    printf "friendlyfamily=${FRIENDLY_FAMILY}\n" >> ${D}${INFOFILE}
    printf "hdmi=${HAVE_HDMI}\n" >> ${D}${INFOFILE}
    printf "hdmifhdin=${HAVE_HDMI_IN_FHD}\n" >> ${D}${INFOFILE}
    printf "hdmihdin=${HAVE_HDMI_IN_HD}\n" >> ${D}${INFOFILE}
    printf "hdmistandbymode=${HDMISTANDBY_MODE}\n" >> ${D}${INFOFILE}
    printf "imagebuild='${BUILD_VERSION}'\n" >> ${D}${INFOFILE}
    printf "imagedevbuild='${DEVELOPER_BUILD_VERSION}'\n" >> ${D}${INFOFILE}
    printf "imagedir=${IMAGEDIR}\n" >> ${D}${INFOFILE}
    printf "imagefs=${IMAGE_FSTYPES}\n" >> ${D}${INFOFILE}
    printf "imagetype=${DISTRO_TYPE}\n" >> ${D}${INFOFILE}
    printf "imageversion='${DISTRO_VERSION}'\n" >> ${D}${INFOFILE}
    printf "imglanguage=multilang\n" >> ${D}${INFOFILE}
    printf "imgrevision='${BUILD_VERSION}'\n" >> ${D}${INFOFILE}
    printf "imgversion='${DISTRO_VERSION}'\n" >> ${D}${INFOFILE}
    printf "kernel='${KERNEL_VERSION}'\n" >> ${D}${INFOFILE}
    printf "kexecmb=${HAVE_KEXECMB}\n" >> ${D}${INFOFILE}
    printf "kernelfile=${KERNEL_FILE}\n" >> ${D}${INFOFILE}
    printf "machinebuild=${MACHINE}\n" >> ${D}${INFOFILE}
    printf "mediaservice=${MEDIASERVICE}\n" >> ${D}${INFOFILE}
    printf "middleflash=${HAVE_MIDDLEFLASH}\n" >> ${D}${INFOFILE}
    printf "mkubifs=${MKUBIFS_ARGS}\n" >> ${D}${INFOFILE}
    printf "model=${MACHINE}\n" >> ${D}${INFOFILE}
    printf "mtdbootfs=${MTD_BOOTFS}\n" >> ${D}${INFOFILE}
    printf "mtdkernel=${MTD_KERNEL}\n" >> ${D}${INFOFILE}
    printf "mtdrootfs=${MTD_ROOTFS}\n" >> ${D}${INFOFILE}
    printf "multilib=False\n" >> ${D}${INFOFILE}
    printf "multitranscoding=${HAVE_MULTITRANSCODING}\n" >> ${D}${INFOFILE}
    printf "oe=${OE_VER}\n" >> ${D}${INFOFILE}
    printf "platform=${STB_PLATFORM}\n" >> ${D}${INFOFILE}
    printf "python='${PYTHON_BASEVERSION}'\n" >> ${D}${INFOFILE}
    printf "rca=${HAVE_RCA}\n" >> ${D}${INFOFILE}
    printf "rcidnum=${RCIDNUM}\n" >> ${D}${INFOFILE}
    printf "rcname=${RCNAME}\n" >> ${D}${INFOFILE}
    printf "rctype=${RCTYPE}\n" >> ${D}${INFOFILE}
    printf "rootfile=${ROOTFS_FILE}\n" >> ${D}${INFOFILE}
    printf "scart=${HAVE_SCART}\n" >> ${D}${INFOFILE}
    printf "noscartswitch=${NO_SCART_SWITCH}\n" >> ${D}${INFOFILE}
    printf "scartyuv=${HAVE_SCART_YUV}\n" >> ${D}${INFOFILE}
    printf "smallflash=${HAVE_SMALLFLASH}\n" >> ${D}${INFOFILE}
    printf "socfamily='${SOC_FAMILY}'\n" >> ${D}${INFOFILE}
    printf "svideo=${HAVE_SVIDEO}\n" >> ${D}${INFOFILE}
    printf "timerwakeupmode=${TIMERWAKEUP_MODE}\n" >> ${D}${INFOFILE}
    printf "transcoding=${HAVE_TRANSCODING}\n" >> ${D}${INFOFILE}
    printf "ubinize=${UBINIZE_ARGS}\n" >> ${D}${INFOFILE}
    printf "vfdsymbol=${HAVE_VFDSYMBOL}\n" >> ${D}${INFOFILE}
    printf "wol=${HAVE_WOL}\n" >> ${D}${INFOFILE}
    printf "wwol=${HAVE_WWOL}\n" >> ${D}${INFOFILE}
    printf "yuv=${HAVE_YUV}\n" >> ${D}${INFOFILE}
    printf "checksum=%s\n" $(md5sum "${D}${INFOFILE}" | awk '{print $1}') >> ${D}${INFOFILE}
}

do_install[vardepsexclude] += " DATE DATETIME IMAGE_BUILD"

FILES:${PN}:append = " /usr"

do_deploy() {
	install -d ${DEPLOY_DIR_IMAGE}/../../enigma-info
	install -m 0644 ${D}${INFOFILE} ${DEPLOY_DIR_IMAGE}/../../enigma-info/${MACHINE}.txt
}

addtask deploy before do_package after do_install