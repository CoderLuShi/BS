"use strict";

// Class Definition
var KTSignupFreeTrial = function() {
    // Elements
    var form;
    var submitButton;
    var getCode;
    var validator;
    var get_code_validator
    var passwordMeter;


    // Handle form
    var handleForm = function(e) {
        // Init form validation rules. For more info check the FormValidation plugin's official documentation:https://formvalidation.io/
        //提交时检查表单
        validator = FormValidation.formValidation(
			form,
			{
				fields: {					 
					'email': {
                        validators: {
							notEmpty: {
								message: '请输入邮箱！'
							},
                            emailAddress: {
								message: '你确定你邮箱输对了？'
							}
						}
					},
                    'password': {
                        validators: {
                            notEmpty: {
                                message: '请输入密码！'
                            },
                            callback: {
                                message: '再多加几个字符',
                                callback: function(input) {
                                    if (input.value.length > 0) {
                                        return validatePassword();
                                    }
                                }
                            }
                        }
                    },
                    'confirm-password': {
                        validators: {
                            notEmpty: {
                                message: '这里还没填！'
                            },
                            identical: {
                                compare: function() {
                                    return form.querySelector('[name="password"]').value;
                                },
                                message: '跟刚才输入的密码不一样鸭！'
                            }
                        }
                    },
                    'verification-code': {
                        validators: {
                            notEmpty: {
                                message: '请输入验证码'
                            }
                        }
                    }
                },
                plugins: {
					trigger: new FormValidation.plugins.Trigger({
                        event: {
                            password: false
                        }  
                    }),
					bootstrap: new FormValidation.plugins.Bootstrap5({
                        rowSelector: '.fv-row',
                        eleInvalidClass: '',
                        eleValidClass: ''
                    })
                }			 
			}
		);

    //    ------------------------------------------------------
              //获取验证码时检查表单
               get_code_validator = FormValidation.formValidation(
                form,
                {
                    fields: {
                        'email': {
                            validators: {
                                notEmpty: {
                                    message: '咱就是说,能不能睁开咱的小眼儿,看看邮箱输了没?'
                                }
                            }
                        },
                    },
                    plugins: {
                        trigger: new FormValidation.plugins.Trigger(),
                        bootstrap: new FormValidation.plugins.Bootstrap5({
                            rowSelector: '.fv-row',
                            eleInvalidClass: '',
                            eleValidClass: ''
                        })
                    }
                }
            );
    // Submit button handler
    getCode.addEventListener('click', function (e) {
        // Prevent default button action
        e.preventDefault();
    
        // Validate form before submit
        if (get_code_validator) {
            get_code_validator.validate().then(function (status) {
                console.log('validated!');
    
                if (status == 'Valid') {
                    // Show loading indication
                    getCode.setAttribute('data-kt-indicator', 'on');
    
                    // Disable button to avoid multiple click
                   getCode.disabled = true;
    
                    // Simulate form submission. For more info check the plugin's official documentation: https://sweetalert2.github.io/
                    setTimeout(function () {
                        // Remove loading indication
                        getCode.removeAttribute('data-kt-indicator');
    
                        // Enable button
                        getCode.disabled = false;
                        //获取验证码
                        $.post("/login/verificationCode", {
                                email: form.querySelector('[name="email"]').value,
                                password: form.querySelector('[name="password"]').value,
                                verificationCode: form.querySelector('[name="verification-code"]').value
                            },
                            function (data, status) {
                                if (data.code == 1) {
                                    // Show message popup. For more info check the plugin's official documentation: https://sweetalert2.github.io/
                                    Swal.fire({
                                        text: "获取验证码成功!",
                                        icon: "success",
                                        buttonsStyling: false,
                                        confirmButtonText: "了解",
                                        customClass: {
                                            confirmButton: "btn btn-primary"
                                        }
                                    });
                                } else {
                                    // Show message popup. For more info check the plugin's official documentation: https://sweetalert2.github.io/
                                    Swal.fire({
                                        text: "啧啧啧！获取验证码失败！！",
                                        icon: "error",
                                        buttonsStyling: false,
                                        confirmButtonText: "Try again!",
                                        customClass: {
                                            confirmButton: "btn btn-primary"
                                        }
                                    });
                                }
                            });
                    }, 20);
                }else {
                    Swal.fire({
                        text: "再仔细瞅瞅！",
                        icon: "error",
                        buttonsStyling: false,
                        confirmButtonText: "Ok, got it!",
                        customClass: {
                            confirmButton: "btn btn-primary"
                        }
                    });
                }
            });
        }
    });
    // -------------------------------------------------------------

        submitButton.addEventListener('click', function (e) {
            e.preventDefault();

            validator.revalidateField('password');

            validator.validate().then(function(status) {
		        if (status == 'Valid') {
                    // Show loading indication
                    submitButton.setAttribute('data-kt-indicator', 'on');

                    // Disable button to avoid multiple click 
                    submitButton.disabled = true;

                    // Simulate ajax request
                    setTimeout(function() {
                        // Hide loading indication
                        submitButton.removeAttribute('data-kt-indicator');

                        // Enable button
                        submitButton.disabled = false;
                        $.post("/login/register", {
                                email: form.querySelector('[name="email"]').value,
                                password: form.querySelector('[name="password"]').value,
                                verificationCode: form.querySelector('[name="verification-code"]').value
                            },
                            function (data, status) {
                                if (data.code == 1) {
                                    // Show message popup. For more info check the plugin's official documentation: https://sweetalert2.github.io/
                                    Swal.fire({
                                        text: "注册成功!",
                                        icon: "success",
                                        buttonsStyling: false,
                                        confirmButtonText: "去登录",
                                        customClass: {
                                            confirmButton: "btn btn-primary"
                                        }
                                    }).then(function (result) {
                                        if (result.isConfirmed) {
                                            //form.submit(); // submit form
                                            const redirectUrl = "/login/index.html";
                                            if (redirectUrl) {
                                                location.href = redirectUrl;
                                            }
                                        }
                                    });
                                } else {
                                    // Show message popup. For more info check the plugin's official documentation: https://sweetalert2.github.io/
                                    Swal.fire({
                                        text: "啧啧啧!"+data.msg,
                                        icon: "error",
                                        buttonsStyling: false,
                                        confirmButtonText: "Try again!",
                                        customClass: {
                                            confirmButton: "btn btn-primary"
                                        }
                                    }).then(function (result) {
                                        if (result.isConfirmed) {
                                            const redirectUrl = "#";
                                            if (redirectUrl) {
                                                location.href = redirectUrl;
                                            }
                                        }
                                    });
                                }
                            });
                    }, 1500);   						
                } else {
                    // Show error popup. For more info check the plugin's official documentation: https://sweetalert2.github.io/
                    Swal.fire({
                        text: "瞧瞧你的表单,再仔仔细细检查下！",
                        icon: "error",
                        buttonsStyling: false,
                        confirmButtonText: "了解",
                        customClass: {
                            confirmButton: "btn btn-primary"
                        }
                    });
                }
		    });
        });

        form.querySelector('input[name="password"]').addEventListener('input', function() {
            if (this.value.length > 0) {
                validator.updateFieldStatus('password', 'NotValidated');
            }
        });
    }

    // Password input validation
    var validatePassword = function() {
        return  (passwordMeter.getScore() === 100);
    }

    // Public functions
    return {
        // Initialization
        init: function() {
            form = document.querySelector('#kt_free_trial_form');
            submitButton = document.querySelector('#register_submit');
            getCode = document.querySelector('#get_code')
            passwordMeter = KTPasswordMeter.getInstance(form.querySelector('[data-kt-password-meter="true"]'));
            handleForm();
        }
    };
}();

// On document ready
KTUtil.onDOMContentLoaded(function() {
    KTSignupFreeTrial.init();
});


 