let authToken = null;
let currentUser = null;

// Smooth scroll
document.querySelectorAll('a[href^="#"]').forEach(anchor => {
    anchor.addEventListener('click', function (e) {
        e.preventDefault();
        const target = document.querySelector(this.getAttribute('href'));
        if (target) {
            target.scrollIntoView({
                behavior: 'smooth',
                block: 'start'
            });
        }
    });
});

// Login Form
document.getElementById('loginForm').addEventListener('submit', async (e) => {
    e.preventDefault();
    
    const username = document.getElementById('username').value;
    const password = document.getElementById('password').value;
    const submitBtn = e.target.querySelector('button[type="submit"]');
    const originalText = submitBtn.innerHTML;
    
    // Loading state
    submitBtn.disabled = true;
    submitBtn.innerHTML = '<span class="spinner-border spinner-border-sm me-2"></span>Entrando...';
    
    try {
        const response = await fetch('/api/auth/login', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify({ username, password })
        });
        
        const data = await response.json();
        
        if (data.success && data.data && data.data.token) {
            authToken = data.data.token;
            currentUser = data.data.user;
            localStorage.setItem('authToken', authToken);
            localStorage.setItem('currentUser', JSON.stringify(currentUser));
            
            showMessage('loginMessage', 'Login realizado com sucesso! Redirecionando...', 'success');
            
            setTimeout(() => {
                showDashboard();
            }, 1000);
        } else {
            showMessage('loginMessage', 'Erro ao fazer login: ' + (data.message || 'Credenciais inválidas'), 'danger');
            submitBtn.disabled = false;
            submitBtn.innerHTML = originalText;
        }
    } catch (error) {
        console.error('Erro:', error);
        showMessage('loginMessage', 'Erro ao conectar com o servidor. Verifique sua conexão.', 'danger');
        submitBtn.disabled = false;
        submitBtn.innerHTML = originalText;
    }
});

// Show Dashboard
function showDashboard() {
    document.getElementById('login').style.display = 'none';
    document.getElementById('dashboard').style.display = 'block';
    document.getElementById('home').style.display = 'none';
    document.getElementById('features').style.display = 'none';
    document.getElementById('about').style.display = 'none';
    
    // Scroll to dashboard
    document.getElementById('dashboard').scrollIntoView({ behavior: 'smooth' });
    
    // Update user name
    if (currentUser) {
        document.getElementById('userName').textContent = currentUser.fullName || currentUser.username;
    }
    
    loadDashboard();
}

// Load Dashboard Data
async function loadDashboard() {
    if (!authToken) {
        authToken = localStorage.getItem('authToken');
        const userStr = localStorage.getItem('currentUser');
        if (userStr) {
            currentUser = JSON.parse(userStr);
        }
    }
    
    if (!authToken) return;
    
    try {
        // Load Skills
        const skillsResponse = await fetch('/api/skills?page=0&size=100', {
            headers: {
                'Authorization': `Bearer ${authToken}`
            }
        });
        
        if (skillsResponse.ok) {
            const skillsData = await skillsResponse.json();
            if (skillsData.success && skillsData.data) {
                animateNumber('skillCount', skillsData.data.totalElements || 0);
                displaySkills(skillsData.data.content || []);
            }
        }
        
        // Load Career Paths
        const careersResponse = await fetch('/api/career-paths/future?page=0&size=100', {
            headers: {
                'Authorization': `Bearer ${authToken}`
            }
        });
        
        if (careersResponse.ok) {
            const careersData = await careersResponse.json();
            if (careersData.success && careersData.data) {
                animateNumber('careerCount', careersData.data.totalElements || 0);
                displayCareers(careersData.data.content || []); 
            }
        }
        
        // Load Trainings
        const trainingsResponse = await fetch('/api/trainings?page=0&size=100', {
            headers: {
                'Authorization': `Bearer ${authToken}`
            }
        });
        
        if (trainingsResponse.ok) {
            const trainingsData = await trainingsResponse.json();
            if (trainingsData.success && trainingsData.data) {
                displayTrainings(trainingsData.data.content || []);
            }
        }
        
        // Load Predictions
        const today = new Date();
        const startDate = new Date(today.getFullYear(), 0, 1).toISOString();
        const endDate = new Date(today.getFullYear(), 11, 31).toISOString();
        
        const predictionsResponse = await fetch(`/api/predictions/range?startDate=${startDate}&endDate=${endDate}`, {
            headers: {
                'Authorization': `Bearer ${authToken}`
            }
        });
        
        if (predictionsResponse.ok) {
            const predictionsData = await predictionsResponse.json();
            if (predictionsData.success && predictionsData.data) {
                animateNumber('predictionCount', predictionsData.data.length || 0);
            }
        }
    } catch (error) {
        console.error('Erro ao carregar dashboard:', error);
    }
}

// Display Skills
function displaySkills(skills) {
    const container = document.getElementById('skillsList');
    if (!skills || skills.length === 0) {
        container.innerHTML = '<p class="text-center text-muted py-5">Nenhuma habilidade encontrada.</p>';
        return;
    }
    
    container.innerHTML = skills.map(skill => `
        <div class="skill-item" data-in-demand="${skill.inDemand}" data-future-proof="${skill.futureProof}">
            <div class="skill-header">
                <h5 class="skill-name">${skill.name}</h5>
                <div class="skill-badges">
                    ${skill.inDemand ? '<span class="badge badge-in-demand">Em Alta</span>' : ''}
                    ${skill.futureProof ? '<span class="badge badge-future-proof">Futuro</span>' : ''}
                </div>
            </div>
            <p class="skill-description">${skill.description || 'Sem descrição'}</p>
            <div class="skill-meta">
                <span class="skill-category">${formatCategory(skill.category)}</span>
                <span class="skill-level">${formatLevel(skill.level)}</span>
            </div>
        </div>
    `).join('');
}

// Display Careers
async function displayCareers(careers) {
    const container = document.getElementById('careersList');
    if (!careers || careers.length === 0) {
        container.innerHTML = '<p class="text-center text-muted py-5">Nenhuma carreira encontrada.</p>';
        return;
    }
    
    // Buscar todas as skills para fazer match com os IDs
    let allSkills = [];
    try {
        const skillsResponse = await fetch('/api/skills?page=0&size=100', {
            headers: { 'Authorization': `Bearer ${authToken}` }
        });
        if (skillsResponse.ok) {
            const skillsData = await skillsResponse.json();
            if (skillsData.success && skillsData.data) {
                allSkills = skillsData.data.content || [];
            }
        }
    } catch (error) {
        console.error('Erro ao buscar skills:', error);
    }
    
    // Criar mapa de skills por ID
    const skillsMap = new Map(allSkills.map(skill => [skill.id, skill]));
    
    container.innerHTML = careers.map(career => {
        const skillNames = career.requiredSkillIds && career.requiredSkillIds.length > 0
            ? career.requiredSkillIds.map(id => {
                const skill = skillsMap.get(id);
                return skill ? skill.name : null;
            }).filter(name => name !== null)
            : [];
        
        return `
        <div class="career-item">
            <h4 class="career-title">${career.title}</h4>
            <p class="career-description">${career.description || 'Sem descrição'}</p>
            <div class="career-stats">
                <div class="career-stat">
                    <span class="career-stat-value">${career.estimatedYears || 'N/A'}</span>
                    <span class="career-stat-label">Anos</span>
                </div>
                <div class="career-stat">
                    <span class="career-stat-value">R$ ${formatSalary(career.averageSalary)}</span>
                    <span class="career-stat-label">Salário</span>
                </div>
                <div class="career-stat">
                    <span class="career-stat-value">+${career.jobGrowth || 0}%</span>
                    <span class="career-stat-label">Crescimento</span>
                </div>
            </div>
            ${skillNames.length > 0 ? `
                <div class="career-skills">
                    <div class="career-skills-label">Habilidades necessárias:</div>
                    <div class="career-skills-tags">
                        ${skillNames.map(name => `<span class="skill-tag">${name}</span>`).join('')}
                    </div>
                </div>
            ` : ''}
        </div>
    `;
    }).join('');
}

// Display Trainings
function displayTrainings(trainings) {
    const container = document.getElementById('trainingsList');
    if (!trainings || trainings.length === 0) {
        container.innerHTML = '<p class="text-center text-muted py-5">Nenhum treinamento encontrado.</p>';
        return;
    }
    
    container.innerHTML = trainings.map(training => `
        <div class="training-item" data-free="${training.isFree}" data-certified="${training.isCertified}">
            <div class="training-header">
                <h5 class="training-title">${training.title}</h5>
                <div class="training-badges">
                    ${training.isFree ? '<span class="badge badge-free">Gratuito</span>' : ''}
                    ${training.isCertified ? '<span class="badge badge-certified">Certificado</span>' : ''}
                </div>
            </div>
            <p class="training-description">${training.description || 'Sem descrição'}</p>
            <div class="training-meta">
                <span><i class="bi bi-clock me-1"></i>${training.durationHours || 'N/A'}h</span>
                <span><i class="bi bi-people me-1"></i>${training.enrollmentCount || 0} alunos</span>
                ${training.averageRating > 0 ? `<span><i class="bi bi-star-fill me-1"></i>${training.averageRating.toFixed(1)}</span>` : ''}
            </div>
            <div class="training-info">
                <span class="training-provider">${training.provider || 'N/A'}</span>
                <span class="training-price ${training.isFree ? 'free' : ''}">
                    ${training.isFree ? 'Gratuito' : `R$ ${training.price?.toFixed(2) || '0.00'}`}
                </span>
            </div>
            ${training.url ? `<a href="${training.url}" target="_blank" class="training-link"><i class="bi bi-box-arrow-up-right me-1"></i>Acessar treinamento</a>` : ''}
        </div>
    `).join('');
}

// Filter Functions
function filterSkills(filter) {
    const items = document.querySelectorAll('.skill-item');
    const buttons = document.querySelectorAll('.btn-filter[data-filter]');
    
    buttons.forEach(btn => btn.classList.remove('active'));
    event.target.classList.add('active');
    
    items.forEach(item => {
        const inDemand = item.dataset.inDemand === 'true';
        const futureProof = item.dataset.futureProof === 'true';
        
        if (filter === 'all') {
            item.style.display = 'block';
        } else if (filter === 'in-demand' && inDemand) {
            item.style.display = 'block';
        } else if (filter === 'future-proof' && futureProof) {
            item.style.display = 'block';
        } else {
            item.style.display = 'none';
        }
    });
}

function filterTrainings(filter) {
    const items = document.querySelectorAll('.training-item');
    const buttons = document.querySelectorAll('.btn-filter[data-filter]');
    
    buttons.forEach(btn => {
        if (btn.onclick && btn.onclick.toString().includes('filterTrainings')) {
            btn.classList.remove('active');
        }
    });
    event.target.classList.add('active');
    
    items.forEach(item => {
        const isFree = item.dataset.free === 'true';
        const isCertified = item.dataset.certified === 'true';
        
        if (filter === 'all') {
            item.style.display = 'block';
        } else if (filter === 'free' && isFree) {
            item.style.display = 'block';
        } else if (filter === 'certified' && isCertified) {
            item.style.display = 'block';
        } else {
            item.style.display = 'none';
        }
    });
}

// Helper Functions
function formatCategory(category) {
    return category.replace(/_/g, ' ').toLowerCase()
        .replace(/\b\w/g, l => l.toUpperCase());
}

function formatLevel(level) {
    const levels = {
        'BEGINNER': 'Iniciante',
        'INTERMEDIATE': 'Intermediário',
        'ADVANCED': 'Avançado',
        'EXPERT': 'Especialista'
    };
    return levels[level] || level;
}

function formatSalary(salary) {
    if (!salary) return '0';
    return new Intl.NumberFormat('pt-BR', { 
        minimumFractionDigits: 0,
        maximumFractionDigits: 0 
    }).format(salary);
}

// Animate Number
function animateNumber(elementId, targetNumber) {
    const element = document.getElementById(elementId);
    const duration = 1000;
    const steps = 30;
    const increment = targetNumber / steps;
    let current = 0;
    const timer = setInterval(() => {
        current += increment;
        if (current >= targetNumber) {
            element.textContent = targetNumber;
            clearInterval(timer);
        } else {
            element.textContent = Math.floor(current);
        }
    }, duration / steps);
}

// Show Message
function showMessage(elementId, message, type) {
    const element = document.getElementById(elementId);
    element.className = `alert alert-${type}`;
    element.innerHTML = `<i class="bi bi-${type === 'success' ? 'check-circle' : 'exclamation-triangle'}-fill me-2"></i>${message}`;
    element.style.display = 'block';
    
    // Auto hide after 5 seconds
    setTimeout(() => {
        element.style.display = 'none';
    }, 5000);
}

// Logout
function logout() {
    authToken = null;
    currentUser = null;
    localStorage.removeItem('authToken');
    localStorage.removeItem('currentUser');
    
    document.getElementById('dashboard').style.display = 'none';
    document.getElementById('login').style.display = 'block';
    document.getElementById('home').style.display = 'block';
    document.getElementById('features').style.display = 'block';
    document.getElementById('about').style.display = 'block';
    
    // Clear form
    document.getElementById('loginForm').reset();
    
    // Scroll to login
    document.getElementById('login').scrollIntoView({ behavior: 'smooth' });
}

// Check if already logged in
window.addEventListener('load', () => {
    const token = localStorage.getItem('authToken');
    const userStr = localStorage.getItem('currentUser');
    
    if (token) {
        authToken = token;
        if (userStr) {
            currentUser = JSON.parse(userStr);
        }
        showDashboard();
    }
});

// Navbar scroll effect
let lastScroll = 0;
window.addEventListener('scroll', () => {
    const navbar = document.querySelector('.navbar');
    const currentScroll = window.pageYOffset;
    
    if (currentScroll > 100) {
        navbar.style.background = 'rgba(10, 14, 39, 0.98)';
    } else {
        navbar.style.background = 'rgba(10, 14, 39, 0.95)';
    }
    
    lastScroll = currentScroll;
});

// Interactive Feature Cards
document.querySelectorAll('.feature-toggle').forEach(button => {
    button.addEventListener('click', function(e) {
        e.preventDefault();
        const card = this.closest('.feature-interactive-card');
        const isActive = card.classList.contains('active');
        
        // Close all other cards
        document.querySelectorAll('.feature-interactive-card').forEach(c => {
            c.classList.remove('active');
        });
        
        // Toggle current card
        if (!isActive) {
            card.classList.add('active');
            this.querySelector('i').classList.remove('bi-chevron-down');
            this.querySelector('i').classList.add('bi-chevron-up');
        } else {
            card.classList.remove('active');
            this.querySelector('i').classList.remove('bi-chevron-up');
            this.querySelector('i').classList.add('bi-chevron-down');
        }
    });
});

// Smooth scroll for anchor links
document.querySelectorAll('a[href^="#"]').forEach(anchor => {
    anchor.addEventListener('click', function (e) {
        e.preventDefault();
        const targetId = this.getAttribute('href');
        if (targetId === '#') return;
        
        const target = document.querySelector(targetId);
        if (target) {
            const offsetTop = target.offsetTop - 80; // Account for fixed navbar
            window.scrollTo({
                top: offsetTop,
                behavior: 'smooth'
            });
        }
    });
});
