SUMMARY = "aesdchar kernel modules"

#   LICENSE
LICENSE = "GPL-2.0-only"
LIC_FILES_CHKSUM = "file://${COMMON_LICENSE_DIR}/GPL-2.0-only;md5=801f80980d171dd6425610833a22dbe6"

SRC_URI = "git://git@github.com/tps01/assignment3-tps01.git;protocol=ssh;branch=main \
		   file://aesd-char-driver-start-stop"

# Modify these as desired
PV = "1.0+git${SRCPV}"
SRCREV = "0a0791864765b12c3984533d23a14670ddb52494"

S = "${WORKDIR}/git/aesd-char-driver"

inherit update-rc.d
inherit module

INITSCRIPT_PACKAGES = "${PN}"
INITSCRIPT_NAME:${PN} = "aesd-char-driver-start-stop"

RPROVIDES:${PN} += "kernel-module-aeesdchar"
RDEPENDS:${PN}:remove = "kernel-module-aesdchar-${KERNEL_VERSION}"
RDEPENDS:${PN}:remove = "kernel-module-aesdchar"

#KERNEL_MODULE_AUTOLOAD += "aesdchar"

EXTRA_OEMAKE += "KERNELDIR=${STAGING_KERNEL_DIR}"
EXTRA_OEMAKE:append:task-install = " -C ${STAGING_KERNEL_DIR} M=${S}"
RPROVIDES:${PN} += "kernel-module-aesdchar"


do_install () {
    module_do_install
	install -d ${D}${sysconfdir}/init.d
	install -m 0755 ${WORKDIR}/aesd-char-driver-start-stop ${D}${sysconfdir}/init.d/
}

FILES:${PN} += "/lib/modules/${KERNEL_VERSION}/extra/aesdchar.ko"
FILES:${PN} += "${sysconfdir}/init.d/aesd-char-driver-start-stop"
