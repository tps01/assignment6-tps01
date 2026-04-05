#   LICENSE
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://${COMMON_LICENSE_DIR}/MIT;md5=0835ade698e0bcf8506ecda2f7b4f302"


SRC_URI = "git://git@github.com/tps01/assignment-7-tps01.git;protocol=ssh;branch=main \
           file://0001-modified-ldd3-makefile-to-only-include-scull-and-mis.patch \
           file://scull-init"

# Modify these as desired
PV = "1.0+git${SRCPV}"
SRCREV = "20ca7b6e986ecfe3f7e566137cb312bb8b1197eb"

S = "${WORKDIR}/git"

inherit module
inherit update-rc.d
INITSCRIPT_NAME = "scull-init"

RDEPENDS:${PN}:remove = "kernel-module-scull-${KERNEL_VERSION}"
RDEPENDS:${PN}:remove = "kernel-module-scull"
RPROVIDES:${PN} += "kernel-module-scull"

do_compile() {
    oe_runmake -C ${S}/scull
}

EXTRA_OEMAKE:append:task-install = " -C ${STAGING_KERNEL_DIR} M=${S}/scull"
EXTRA_OEMAKE += "KERNELDIR=${STAGING_KERNEL_DIR}"

do_install () {
	module_do_install
	install -d ${D}${sysconfdir}/init.d
	install -m 0755 ${WORKDIR}/scull-init ${D}${sysconfdir}/init.d/
}

FILES:${PN} += "/lib/modules/${KERNEL_VERSION}/extra/scull.ko"
FILES:${PN} += "${sysconfdir}/init.d/scull-init"